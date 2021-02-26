package frontend;

import javafx.beans.property.SimpleStringProperty;

public class Record {
    //Assume each record have 6 elements, all String

    private SimpleStringProperty X, Y, Z, Carrier;

    public String getX() {
        return X.get();
    }

    public String getY() {
        return Y.get();
    }

    public String getZ() {
        return Z.get();
    }

    public String getCarrier() {
        return Carrier.get();
    }


    Record(String x, String y, String z, String carrier
    ) {
        this.X = new SimpleStringProperty(x);
        this.Y = new SimpleStringProperty(y);
        this.Z = new SimpleStringProperty(z);
        this.Carrier = new SimpleStringProperty(carrier);
    }

}
