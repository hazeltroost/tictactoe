package tictactoe;

import spark.Spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Echo9CharsServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Echo9CharsServer.class);

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
