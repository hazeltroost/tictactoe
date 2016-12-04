package tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		Spark.get("/echo", (req, res) -> {
			try {
				String param = req.queryParams("content");
				LOGGER.info(param);
				//if (param.length() != 9)
					//throw new IllegalArgumentException();
				return param.replaceAll("\\+", " ");
			} catch (IllegalArgumentException e) {
				res.status(400);
				return "";
			}
		});
	}

}
