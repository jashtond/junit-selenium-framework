package com.lotaris.selenium.utils;

import com.lotaris.selenium.page.IPageObject;
import com.lotaris.selenium.page.PageElement;
import com.lotaris.selenium.page.PageElementInitializationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

/**
 * Groups some of the reflection utils commonly used in the 
 * selenium framework extended by Lotaris.
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public final class ReflectionUtils {
	/**
	 * Hidden constructor
	 */
	private ReflectionUtils() {}
	
	/**
	 * Inject the page object into the page element
	 * 
	 * @param pageElement The page element where to inject the page object
	 * @param pageObject The page object to inject into the page element
	 */
	public static void injectPageObject(PageElement pageElement, IPageObject pageObject) {
		inejectField(pageElement, "pageObject", pageObject);
	}

	/**
	 * Inject a field
	 * 
	 * @param objectToBeInjected The object in which a value is to be injected
	 * @param fieldName The field name where value is to be injected
	 * @param objectToInject The value to inject
	 */
	private static void inejectField(Object objectToBeInjected, String fieldName, Object objectToInject) {
		try {
			// Retrieve the field
			Field field = findField(fieldName, objectToBeInjected.getClass());

			// Be sure the field can be injected
			boolean acc = field.isAccessible();
			if (!acc) {
				field.setAccessible(true);
				acc = false;
			}
			
			// Inject the value
			field.set(objectToBeInjected, objectToInject);
			
			// Restore the accessibility if required
			if (!acc) {
				field.setAccessible(false);
			}
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
			throw new PageElementInitializationException(e.getMessage(), e.getCause());
		}
	}
	
	public static void copyByReflection(Object source, Object target) {
		Class scannedClass = source.getClass();

		// Recursive copy for each level in the class hierarchy
		do {
			for (Field f : scannedClass.getDeclaredFields()) {
				// Check if the current field can be copied
				if (canBeCopied(f)) {
					copyFieldValue(f, source, target);
				}
			}
			scannedClass = scannedClass.getSuperclass();
		} while (scannedClass != Object.class); // No more to copy
	}
	
	/**
	 * Check if a field can be copied
	 * 
	 * @param field The field to check
	 * @return True if the field is copiable, false otherwise
	 */
	private static boolean canBeCopied(Field field) {
		return 
			!Modifier.isFinal(field.getModifiers()) &&
			!Modifier.isStatic(field.getModifiers()) &&
			!field.isAnnotationPresent(FindBy.class) && 
			!field.isAnnotationPresent(FindBys.class) && 
			!field.isAnnotationPresent(FindAll.class);
	}
	
	/**
	 * Copy from a field of a source object to the field of the target object
	 * 
	 * @param field The field to copy
	 * @param source The source object
	 * @param target The target object
	 */
	private static void copyFieldValue(Field field, Object source, Object target) {
		// Be sure that the field can be injected
		boolean acc = false;
		if (!field.isAccessible()) {
			field.setAccessible(true);
			acc = true;
		}
		
		// Inject the source value to the target value
		try {
			field.set(target, field.get(source));
		}
		catch (IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException("Unable to copy " + field.getName() + " from source " + source + " to target " + target);
		}
		
		// Restore the field to the original visibility
		if (acc) {
			field.setAccessible(false);
		}
	}	
	
	/**
	 * Find a constructor in a class hierarchy
	 * 
	 * @param baseClass The class where the constructor must be found
	 * @param classToFindConstructor The class of the argument type to find the constructor on the base class
	 * @return The constructor found, null otherwise
	 */
	public static Constructor recurseFindConstructor(Class baseClass, Class classToFindConstructor) {
		Class scannedClass = classToFindConstructor;
		
		do {
			// Try to retrieve a constructor that match the scannedClass
			try {
				return baseClass.getConstructor(scannedClass);
			}
			catch (NoSuchMethodException | SecurityException e) {
				// If the class scanned has interfaces, try to find constructor for that interfaces
				for (Class interfaceClass : scannedClass.getInterfaces()) {
					// Do the find recursively in the interface class hierarchy
					Constructor result = recurseFindConstructor(baseClass, interfaceClass);
					if (result != null) {
						return result;
					}
				}
				
				// Contine the lookup on the super class
				scannedClass = scannedClass.getSuperclass();
			}
		// Check if the class is Object, in that case, no constructor is available. In case of Interface, null scannedClass appears which means
		// there is no super class of the current interface scanned.
		} while (scannedClass != Object.class && scannedClass != null);
		
		return null;
	}
	
	/**
	 * Find a field on a class. Do the lookup in the super classes of the
	 * current class.
	 * 
	 * @param fieldName The field name to find
	 * @param cl The class
	 * @return The field found, null otherwise
	 */
	public static Field findField(String fieldName, Class cl) {
		Class scannedClass = cl;
		
		do {
			try {
				// Retrieve the field for the current level of class and field name
				return scannedClass.getDeclaredField(fieldName);
			}
			catch (NoSuchFieldException | SecurityException e) {
				scannedClass = scannedClass.getSuperclass();
			}
		} while (scannedClass != Object.class);

		return null;
	}
}
