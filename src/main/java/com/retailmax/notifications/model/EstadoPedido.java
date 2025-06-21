package com.retailmax.notifications.model;

public enum EstadoPedido {
    ENVIADO("enviado"),
    EN_CAMINO("en camino"),
    ENTREGADO("entregado"),
    CANCELADO("cancelado");

    private final String valor;

    EstadoPedido(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}




