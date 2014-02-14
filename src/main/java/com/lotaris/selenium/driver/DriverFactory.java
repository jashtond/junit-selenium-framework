package com.lotaris.selenium.driver;

import com.google.common.collect.ImmutableMap;
import com.lotaris.selenium.IConfiguration;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The factory to build the different drivers
 * 
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public final class DriverFactory {
	private static final Logger LOG = LoggerFactory.getLogger(DriverFactory.class);
	
	/**
	 * Private constructor
	 */
	private DriverFactory() {}
	
	/**
	 * @param configuration The configuration to help the creation of the driver
	 * @return The firefox driver created
	 */
	public static WebDriver createFirefoxDriver(IConfiguration configuration) {
		// Prepare the capability storage
		FirefoxProfile ffp = new FirefoxProfile();

		// Enabled native events (on Linux they are disabled by default), because some of the actions done on the browser are using these events
		// http://code.google.com/p/selenium/wiki/AdvancedUserInteractions#Native_events_versus_synthetic_events
		// http://www.nuget.org/packages/Selenium.WebDriver  when using these events we will have to switch to Selenium 2.31 or higher
		// first use of these native events are required when testing the tooltips displayed in AM dashboard
		ffp.setEnableNativeEvents(true);

		// Prepare capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		// Check if a proxy should be configured
		if (configuration.isProxyEnabled()) {
			configureProxyCapabilities(configuration, capabilities);
		}
		
		FirefoxBinary ffb;

		// Check if a specific firefox binary is specified
		if (configuration.getBrowserFirefoxPath() != null) {
			LOG.debug("Get the binary from the configuration.");
			ffb = new FirefoxBinary(new File(configuration.getBrowserFirefoxPath()));
		}
		else {
			LOG.debug("Get the default binary.");
			ffb = new FirefoxBinary();
		}

		if (configuration.isXvfbEnabled()) {
			ffb.setEnvironmentProperty("DISPLAY", configuration.getXvfbDisplay());
		}

		return new FirefoxDriver(ffb, ffp, capabilities);
	}
	
	/**
	 * @param configuration The configuration to help the creation of the driver
	 * @return The Google Chrome driver created
	 */
	public static WebDriver createChromeDriver(IConfiguration configuration) throws IOException {
		if (!configuration.getDriverGoogleChromePath().isEmpty()) {
			System.setProperty("webdriver.chrome.driver", configuration.getDriverGoogleChromePath());
		}

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		if (configuration.getBrowserGoogleChromePath() != null) {
			capabilities.setCapability("chrome.binary", configuration.getBrowserGoogleChromePath());
		}
		
		if (configuration.isXvfbEnabled()) {
			ChromeDriverService service = new ChromeDriverService.Builder().
				usingDriverExecutable(new File(System.getProperty("webdriver.chrome.driver"))).
				usingAnyFreePort().
				withEnvironment(ImmutableMap.of("DISPLAY", configuration.getXvfbDisplay())).
				build();
			
			return new ChromeDriver(service, capabilities);
		}
		else {
			return new ChromeDriver(capabilities);
		}
	}
	
	/**
	 * Add PROXY configuration to the capabilities
	 * 
	 * @param desiredCapabilities The capabilities to enrich
	 */
	private static void configureProxyCapabilities(IConfiguration configuration, DesiredCapabilities desiredCapabilities) {
		String proxyString = configuration.getProxyHost() + ":" + configuration.getProxyPort();
		
		Proxy proxy = new Proxy();
		
		proxy
			.setHttpProxy(proxyString)
			.setSslProxy(proxyString)
			.setFtpProxy(proxyString);
		
		desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
	}
	
	/**
	 * Represent the driver supported at this time
	 */
	public enum DriverName {
		/**
		 * Firefox driver is simple to use. Nothing special to do on the target environment 
		 * where just the binary must be present
		 */
		FIREFOX("Firefox") {
			@Override
			public WebDriver internalBuild(IConfiguration configuration) throws Exception {
				return createFirefoxDriver(configuration);
			}
		},

		/**
		 * Take care of the Google Chrome driver. There is an additional binary to install
		 * on the target computer which is actually the driver which drives Google Chrome.
		 */
		CHROME("Google Chrome") {
			@Override
			public WebDriver internalBuild(IConfiguration configuration) throws Exception {
				return createChromeDriver(configuration);
			}
		};
		
		private String humanName;
		
		/**
		 * Constructor
		 * 
		 * @param humanName Human name for the driver
		 */
		private DriverName(String humanName) {
			this.humanName = humanName;
		}

		/**
		 * @return Retrieve the human name of the driver
		 */
		public String getHumanName() {
			return humanName;
		}
		
		/**
		 * @param configuration The configuration to create drivers
		 * @return Build the web driver configured
		 */
		public WebDriver build(IConfiguration configuration) {
			LOG.debug("Try to create the " + humanName + " driver.");
			try {
				WebDriver driver = internalBuild(configuration);
				LOG.info(humanName + " driver created.");
				return driver;
			}
			catch (Exception ex) {
				LOG.warn("Unable to create the " + humanName + " driver.", ex);
				return null;
			}
		};
		
		/**
		 * Create the driver based on the configuration given
		 * 
		 * @param configuration The configuration
		 * @return The driver built
		 * @throws Exception Any error during the creation of the driver
		 */
		protected abstract WebDriver internalBuild(IConfiguration configuration) throws Exception;
	}
}
