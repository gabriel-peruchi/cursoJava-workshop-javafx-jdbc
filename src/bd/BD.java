package bd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {

	private static Connection conexao = null;

	public static Connection getConexao() {

		if (conexao == null) {

			try {

				Properties props = carregarPropiedades();
				String url = props.getProperty("dburl");
				conexao = DriverManager.getConnection(url, props);

			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}

		}

		return conexao;
	}

	public static void fecharConexao() {

		if (conexao != null) {

			try {
				conexao.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}

		}

	}

	private static Properties carregarPropiedades() {

		try (FileInputStream fs = new FileInputStream("bd.propiedades")) {

			Properties props = new Properties();
			props.load(fs);

			return props;

		} catch (IOException e) {

			throw new BdException(e.getMessage());
		}

	}

	public static void fecharStatement(Statement st) {

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
	}

	public static void fecharResultSet(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
	}

}
