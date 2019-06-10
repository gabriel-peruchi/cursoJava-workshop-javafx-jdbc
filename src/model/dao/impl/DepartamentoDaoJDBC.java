package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.BD;
import bd.BdException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	private Connection conexao = null;

	public DepartamentoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Departamento obj) {
		
		PreparedStatement ps = null;

		try {

			ps = conexao.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getNome());

			
			int linhasAfetadas =  ps.executeUpdate();
			
			if(linhasAfetadas > 0) {
				
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {

					int id = rs.getInt(1);
					obj.setId(id);

				}
				BD.fecharResultSet(rs);
				
			}else {
				throw new BdException("Ocorreu um erro inesperado");
			}

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public void update(Departamento obj) {

		PreparedStatement ps = null;

		try {

			ps = conexao.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");

			ps.setString(1, obj.getNome());
			ps.setInt(2, obj.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public void deletePorId(Integer id) {

		PreparedStatement ps = null;

		try {

			ps = conexao.prepareStatement("DELETE FROM department WHERE Id = ?");

			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public Departamento consultaPorId(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement("SELECT * FROM department WHERE Id = ?");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {

				Departamento dep = instanciaDepartamento(rs);

				return dep;

			}

			return null;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharStatement(ps);
			BD.fecharResultSet(rs);
		}

	}

	@Override
	public List<Departamento> consultaTodos() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement("SELECT * FROM department ORDER BY Name");

			rs = ps.executeQuery();

			List<Departamento> listDep = new ArrayList<>();

			while (rs.next()) {

				Departamento dep = instanciaDepartamento(rs);
				listDep.add(dep);

			}

			return listDep;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharStatement(ps);
			BD.fecharResultSet(rs);
		}

	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {

		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("Id"));
		departamento.setNome(rs.getString("Name"));

		return departamento;
	}
}
