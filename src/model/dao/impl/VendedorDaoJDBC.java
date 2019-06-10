package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bd.BD;
import bd.BdException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conexao = null;

	public VendedorDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Vendedor obj) {

		PreparedStatement ps = null;

		try {

			ps = conexao.prepareStatement("INSERT INTO seller" + "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES" + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getNome());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
			ps.setDouble(4, obj.getSalarioBase());
			ps.setInt(5, obj.getDepartamento().getId());

			int linhasAfetadas = ps.executeUpdate();

			if (linhasAfetadas > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {

					int id = rs.getInt(1);
					obj.setId(id);

				}
				BD.fecharResultSet(rs);

			} else {
				throw new BdException("Erro inesperado");
			}

		} catch (SQLException e) {

			throw new BdException("Erro: " + e.getMessage());

		} finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement ps = null;

		try {

			ps = conexao.prepareStatement("UPDATE seller " 
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE ID = ?");

			ps.setString(1, obj.getNome());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
			ps.setDouble(4, obj.getSalarioBase());
			ps.setInt(5, obj.getDepartamento().getId());
			ps.setInt(6, obj.getId());

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new BdException("Erro: " + e.getMessage());

		} finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public void deletePorId(Integer id) {
		
		PreparedStatement ps = null;
		
		try {
			
			ps = conexao.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new BdException("Erro: " + e.getMessage());
		}finally {
			BD.fecharStatement(ps);
		}

	}

	@Override
	public Vendedor consultaPorId(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement(
					"SELECT seller.*,department.Name as DepName\r\n" + "FROM seller INNER JOIN department\r\n"
							+ "ON seller.DepartmentId = department.Id\r\n" + "WHERE seller.Id = ?;");

			ps.setInt(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {

				Departamento departamento = instanciaDepartamento(rs);

				Vendedor vendedor = instanciaVendedor(rs, departamento);

				return vendedor;

			}

			return null;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharResultSet(rs);
			BD.fecharStatement(ps);
		}
	}

	private Vendedor instanciaVendedor(ResultSet rs, Departamento departamento) throws SQLException {

		Vendedor vendedor = new Vendedor();
		vendedor.setId(rs.getInt("Id"));
		vendedor.setNome(rs.getString("Name"));
		vendedor.setEmail(rs.getString("Email"));
		vendedor.setSalarioBase(rs.getDouble("BaseSalary"));
		vendedor.setDataNascimento(rs.getDate("BirthDate"));
		vendedor.setDepartamento(departamento);

		return vendedor;
	}

	private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {

		Departamento departamento = new Departamento();
		departamento.setId(rs.getInt("DepartmentId"));
		departamento.setNome(rs.getString("DepName"));

		return departamento;
	}

	@Override
	public List<Vendedor> consultaTodos() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement(
					"SELECT seller.*, department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");

			rs = ps.executeQuery();

			List<Vendedor> listVendedor = new ArrayList<>();
			Map<Integer, Departamento> mapDepartamento = new HashMap<>();

			while (rs.next()) {

				Departamento dep = mapDepartamento.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instanciaDepartamento(rs);
					mapDepartamento.put(rs.getInt("DepartmentId"), dep);
				}

				Vendedor vendedor = instanciaVendedor(rs, dep);

				listVendedor.add(vendedor);

			}

			return listVendedor;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharResultSet(rs);
			BD.fecharStatement(ps);
		}
	}

	@Override
	public List<Vendedor> consultaPorDepartamento(Departamento departamento) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conexao.prepareStatement(
					"SELECT seller.*, department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			ps.setInt(1, departamento.getId());

			rs = ps.executeQuery();

			List<Vendedor> listVendedor = new ArrayList<>();
			Map<Integer, Departamento> mapDepartamento = new HashMap<>();

			while (rs.next()) {

				Departamento dep = mapDepartamento.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instanciaDepartamento(rs);
					mapDepartamento.put(rs.getInt("DepartmentId"), dep);
				}

				Vendedor vendedor = instanciaVendedor(rs, dep);

				listVendedor.add(vendedor);

			}

			return listVendedor;

		} catch (SQLException e) {
			throw new BdException(e.getMessage());
		} finally {
			BD.fecharResultSet(rs);
			BD.fecharStatement(ps);
		}
	}

}
