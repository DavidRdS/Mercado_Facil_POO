package com.mercadofacil;

public class Main {
    public static void main(String[] args) {
        System.out.println("PDV MercadoFácil iniciado!");
        System.out.println("Pressione Enter para sincronizar vendas...");
        new java.util.Scanner(System.in).nextLine();

        try {
            com.mercadofacil.network.SyncService.sincronizar();
        } catch (Exception e) {
            System.out.println("Erro ao sincronizar: " + e.getMessage());
        }
    }
}
