package com.java8solution.codingsolution;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class CodingsolutionApplication {
	
	static Logger logger = Logger.getLogger(CodingsolutionApplication.class.getName());	

	public static void main(String[] args) {
		CodingsolutionApplication app = new CodingsolutionApplication();
		
		logger.info("Creating Map and Grouping data by ID");
		Map<String, List<LogPojo>> customerDataGroupedById = app.groupCustomerData();
		
		logger.info("Processing data for desired format");
		ArrayList<ResultPojo> resultList = app.processData(customerDataGroupedById);
		
		logger.info("Inserting into database");
		persistResults(resultList);

	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<LogPojo>> groupCustomerData() {
		Map<String, List<LogPojo>> logpojoById = null;
		File logdata = getCustomerFileReader.apply("logdata.json");
		ObjectMapper objMapper = new ObjectMapper();
		ArrayList<LogPojo> aList = new ArrayList<LogPojo>();
		JSONParser parser = new JSONParser();

		try (Reader is = new FileReader(logdata)) {
			JSONArray datas = (JSONArray) parser.parse(is);
			datas.forEach((data) -> {
				try {
					aList.add(objMapper.readValue(data.toString(), LogPojo.class));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (ParseException | IOException e) {
		}

		logger.info("creating groups based on Id using groupingBy using parallel streams");
		//logpojoById = (Map<String, List<LogPojo>>) aList.stream().collect(Collectors.groupingBy(LogPojo::getId));
		logpojoById = (Map<String, List<LogPojo>>) aList.parallelStream().collect(Collectors.groupingBy(LogPojo::getId));
		return logpojoById;
	}
	
	private ArrayList<ResultPojo> processData(Map<String, List<LogPojo>> customerDataGroupedById) {
		// TODO Auto-generated method stub
		Map<String, List<LogPojo>> initialMapData = customerDataGroupedById;
		ArrayList<ResultPojo> resultPojoList = new ArrayList<ResultPojo>();
		initialMapData.entrySet().stream().forEach(

				e -> {

					ResultPojo resultPojo = new ResultPojo();
					e.getValue().stream().forEach(p -> {
						logger.info(p.getState() + "::" + p.getTimestamp());

						resultPojo.setId(p.getId());
						resultPojo.setHost(p.getHost());
						resultPojo.setType(p.getType());
						if (p.getState().equals("STARTED"))
							resultPojo.setStartTS(p.getTimestamp());
						if (p.getState().equals("FINISHED"))
							resultPojo.setEndTS(p.getTimestamp());

					});

					resultPojoList.add(resultPojo);

				}

		);

		logger.info("Setting the boolean flag");
		resultPojoList.stream().forEach(s -> {
			s.setLapseTS(s.getEndTS() - s.getStartTS());
			if (s.getLapseTS() > 4)
				s.setAlert(true);
		});

		return resultPojoList;

	}
	
	private static void persistResults(ArrayList<ResultPojo> resultList){
		Connection connection = null;
		
		try {
			connection = getDatabaseConnection();
			final Statement innerstatement = connection.createStatement();
			
			resultList.stream().forEach(s -> {
				String sql = "insert into events values('"+s.getId()+"',"+s.getLapseTS()+",'"+s.getType()+"','"+s.getHost()+"',"+s.isAlert()+")";
				try {
					logger.log(Level.SEVERE, sql);
					innerstatement.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				logger.info("Closing database connection");
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

	private static Connection getDatabaseConnection() {
		// TODO Auto-generated method stub
	      Connection con = null;
	      
	      try {
	         Class.forName("org.hsqldb.jdbc.JDBCDriver");

	         logger.info("Creating the connection with HSQLDB");
	         con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/solutiondb", "SA", "");
	         if (con!= null){
	            logger.info("Connection created successfully");
	            
	         }else{
	            logger.info("Problem with creating connection");
	         }
	      
	      }  catch (Exception e) {
	         e.printStackTrace(System.out);
	      }
		return con;
	}


	/**
	 * Retrieve a file with specified name
	 */
	public Function<String, File> getCustomerFileReader = filename -> {
		ClassLoader cl = getClass().getClassLoader();
		logger.info("Loading the log file");
		File logdata = new File(getClass().getClassLoader().getResource(filename).getFile());
		return logdata;
	};

}
