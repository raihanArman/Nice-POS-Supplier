package id.co.myproject.nicepos_supplier.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Helper {
    public static int GALLERY_REQUEST = 64;
    public static int TYPE_INTENT_ADD = 77;
    public static int TYPE_INTENT_EDIT = 76;

    public static String rupiahFormat(int harga){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}
