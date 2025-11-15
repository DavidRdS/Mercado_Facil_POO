package com.mercadofacil.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mercadofacil.model.Venda;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SyncService {

    private static final String FILE = "vendas_pendentes.json";
    private static final String URL = "http://localhost:8080/api/vendas";
    private static final Gson gson = new Gson();

    private static List<Venda> carregarPendentes() {
        try (FileReader fr = new FileReader(FILE)) {
            List<Venda> list = gson.fromJson(fr, new TypeToken<List<Venda>>(){}.getType());
            return list == null ? new ArrayList<>() : list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static void salvarPendentes(List<Venda> vendas) throws Exception {
        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(gson.toJson(vendas));
        }
    }

    public static void sincronizar() throws Exception {
        List<Venda> pendentes = carregarPendentes();
        List<Venda> aindaPendentes = new ArrayList<>();

        System.out.println("Tentando sincronizar " + pendentes.size() + " vendas pendentes...");

        for (Venda v : pendentes) {
            boolean enviada = false;
            try {
                enviada = ApiClient.postVenda(URL, v);
            } catch (Exception ex) {
                enviada = false;
            }

            if (enviada) {
                System.out.println("✔ Venda sincronizada com sucesso.");
            } else {
                System.out.println("✖ Falha ao enviar venda; mantida como pendente.");
                aindaPendentes.add(v);
            }
        }

        salvarPendentes(aindaPendentes);
        System.out.println("Sincronização finalizada. " + aindaPendentes.size() + " pendentes restantes.");
    }
}
