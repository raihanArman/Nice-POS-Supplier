package id.co.myproject.nicepos_supplier.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("id_supplier")
    @Expose
    private int idSupplier;

    public Value() {
    }

    public Value(int value, String message, int idSupplier) {
        this.value = value;
        this.message = message;
        this.idSupplier = idSupplier;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public int getIdSupplier() {
        return idSupplier;
    }
}
