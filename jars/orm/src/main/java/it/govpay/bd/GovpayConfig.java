/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.govpay.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

public class GovpayConfig {

	private static GovpayConfig instance;

	public static GovpayConfig getInstance() {
		return instance;
	}

	public static GovpayConfig newInstance4GovPay(InputStream propertyFile) throws Exception {
		instance = new GovpayConfig(propertyFile, "/govpay.properties", "it.govpay.resource.path", false);
		return instance;
	}
	
	public static GovpayConfig newInstance4GovPayConsole(InputStream propertyFile) throws Exception {
		instance = new GovpayConfig(propertyFile, "/govpayConsole.properties", "it.govpay.console.resource.path", true);
		
		return instance;
	}
	
	public static GovpayConfig newInstance(InputStream propertyFile, String propertiesFileName, String propertyResourcePathName, boolean dataonly) throws Exception {
		instance = new GovpayConfig(propertyFile, propertiesFileName, propertyResourcePathName, dataonly);
		return instance;
	}

	private String databaseType;
	private boolean databaseShowSql;
	private String dataSourceJNDIName;
	private String dataSourceAppName;
	private Properties[] props;
	private String resourceDir;

	public GovpayConfig(InputStream propertyFile, String propertyFileName, String resourcePathProperty, boolean dataonly) throws Exception {

		Logger log = LoggerWrapperFactory.getLogger("boot");

		this.props = new Properties[2];
		Properties props1 = new Properties();
		props1.load(propertyFile);
		this.props[1] = props1;

		// Recupero la configurazione della working dir
		// Se e' configurata, la uso come prioritaria

		try {
			if(resourcePathProperty != null)
				this.resourceDir = this.getProperty(resourcePathProperty, props1, false, true);

			if(this.resourceDir != null) {
				File resourceDirFile = new File(this.resourceDir);
				if(!resourceDirFile.isDirectory())
					throw new Exception("Il path indicato nella property \"it.govpay.resource.path\" (" + this.resourceDir + ") non esiste o non e' un folder.");
			}
		} catch (Exception e) {
			LoggerWrapperFactory.getLogger("boot").warn("Errore di inizializzazione: " + e.getMessage() + ". Property ignorata.");
		}

		Properties props0 = null;
		this.props[0] = props0;

		File gpConfigFile = new File(this.resourceDir + propertyFileName);
		if(gpConfigFile.exists()) {
			props0 = new Properties();
			props0.load(new FileInputStream(gpConfigFile));
			log.info("Individuata configurazione prioritaria: " + gpConfigFile.getAbsolutePath());
			this.props[0] = props0;
		}

		this.databaseType = this.getProperty("it.govpay.orm.databaseType", this.props, true);
		String databaseShowSqlString = this.getProperty("it.govpay.orm.showSql", this.props, true);
		this.databaseShowSql = Boolean.parseBoolean(databaseShowSqlString);
		this.dataSourceJNDIName = this.getProperty("it.govpay.orm.dataSourceJNDIName", this.props, true);
		this.dataSourceAppName = this.getProperty("it.govpay.orm.dataSourceAppName", this.props, true);

		if(dataonly) return;
	}

	private String getProperty(String name, Properties props, boolean required, boolean fromInternalConfig) throws Exception {
		Logger log = LoggerWrapperFactory.getLogger("boot");

		String value = System.getProperty(name);

		if(value != null && value.trim().isEmpty()) {
			value = null;
		}
		
		String logString = "";
		if(fromInternalConfig) logString = "da file interno ";
		else logString = "da file esterno ";

		if(value == null) {
			if(props != null) {
				value = props.getProperty(name);
				if(value != null && value.trim().isEmpty()) {
					value = null;
				}
			}
			if(value == null) {
				if(required) 
					throw new Exception("Proprieta ["+name+"] non trovata");
				else return null;
			} else {
				log.info("Letta proprieta di configurazione " + logString + name + ": " + value);
			}
		} else {
			log.info("Letta proprieta di sistema " + name + ": " + value);
		}

		return value.trim();
	}

	private String getProperty(String name, Properties[] props, boolean required) throws Exception {
		Logger log = LoggerWrapperFactory.getLogger("boot");

		String value = null;
		for(int i=0; i<props.length; i++) {
			try { value = this.getProperty(name, props[i], required, i==1); } catch (Exception e) { }
			if(value != null && !value.trim().isEmpty()) {
				return value;
			}
		}

		if(log != null) log.info("Proprieta " + name + " non trovata");

		if(required) 
			throw new Exception("Proprieta ["+name+"] non trovata");
		else 
			return null;
	}

	public String getDatabaseType() {
		return this.databaseType;
	}

	public boolean isDatabaseShowSql() {
		return this.databaseShowSql;
	}

	public String getDataSourceJNDIName() {
		return this.dataSourceJNDIName;
	}

	public String getDataSourceAppName() {
		return this.dataSourceAppName;
	}

	public String getResourceDir() {
		return this.resourceDir;
	}
}
