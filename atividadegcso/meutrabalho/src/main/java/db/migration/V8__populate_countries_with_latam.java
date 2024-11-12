package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class V8__populate_countries_with_latam extends BaseJavaMigration {

    // Método que será executado ao rodar a migração
    @Override
    public void migrate(Context context) throws SQLException {

        // Define a lista de países da América Latina
        String[] countries = {
                "Argentina", "Bolivia", "Brazil", "Chile", "Colombia",
                "Costa Rica", "Cuba", "Dominican Republic", "Ecuador",
                "El Salvador", "Guatemala", "Honduras", "Mexico",
                "Nicaragua", "Panama", "Paraguay", "Peru", "Uruguay",
                "Venezuela"
        };

        // Define o comando SQL para inserir um país
        String insertCountrySql = "INSERT INTO countries (country_id, country_name, region_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = context.getConnection().prepareStatement(insertCountrySql)) {
            for (int i = 0; i < countries.length; i++) {
                statement.setInt(1, i + 1); // Define um ID único para cada país
                statement.setString(2, countries[i]); // Define o nome do país
                statement.setInt(3, 1); // Defina o `region_id` conforme apropriado, substitua se necessário
                statement.addBatch(); // Adiciona o comando ao lote
            }
            statement.executeBatch(); // Executa todos os comandos no lote
        }
    }
}
