package tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static tictactoe.PlayState.*;

import spark.Spark;

public class TicTacToeServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TicTacToeServer.class);
	
	public static void main(String[] args) {
		if (args.length ==1) {
			Spark.port(Integer.valueOf(System.getenv("PORT")));
		}
		else Spark.port(55555);
		Spark.get("/", (req, res) -> {
			try {
				String param = req.queryParams("board");
				LOGGER.info(param);
				Board board = BoardParser.parse(param);
				board.prettyPrint();
				if (board.getCurrentPlayState() != IN_PLAY) throw new IllegalArgumentException("board that was sent had already been won");
				Board response = board.getPlayersResponse();
				response.prettyPrint();
				return response.toString();
			} catch (Exception e) {
				e.printStackTrace();
				res.status(400);
				return e.getMessage();
			}
		});
	}

}
