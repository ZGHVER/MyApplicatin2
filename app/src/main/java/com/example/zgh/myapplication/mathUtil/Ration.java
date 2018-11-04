package com.example.zgh.myapplication.mathUtil;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ZGH
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Ration extends Number implements
        AdjMatrix,Comparable<Ration>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    public static final Ration ONE = new Ration(1);
    public static final Ration ZERO = new Ration(0);
    public static final Ration NEGATIVE = new Ration(-1);
    public static final char SEME="/".charAt(0);

    private int num;
    private int den;

    public Ration(double value) {
        int k = 1;
        if (value == 0.0) {
            this.num = 0;
            this.den = 1;
        } else {
            for (int d = 0; value != (double) (int) value && d < 5; d++) {
                value *= 10;
                k *= 10;
            }
            int n1 = (int) value;
            int r = Ration.getFactor(n1, k);
            this.num = n1 / r;
            this.den = k / r;
        }
    }

    public Ration(double value, int precision) {
        int k = 1;
        for (int d = 0; !(value == (int) value) && d < precision; d++) {
            value *= 10;
            k *= 10;
        }
        int n1 = (int) value;
        int r = Ration.getFactor(n1, k);
        this.num = n1 / r;
        this.den = k / r;
    }

    public Ration(int num, int den) throws MathException  {
        if (num == 0) {
            this.num = 0;
            this.den = 1;
        } else if (den == 0)
            throw new MathException();
        else {
            int mi = Ration.getFactor(num, den);
            this.num = num / mi;
            this.den = den / mi;
        }
    }

    public Ration(int value) {
        this.num = value;
        this.den = 1;
    }

    public Ration() {
        this.num=1;
        this.den=1;
    }

    public int getNumerator() {
        return this.num;
    }

    public int getDenominator() {
        return this.den;
    }

    public Ration adds(Ration that){
        if (this.num == 0)			return that;
        if (that.num == 0)			return this;
        int m = this.den * that.den;
        int z = this.num * that.den + that.num * this.den;
        try {						return new Ration(z, m);}
        catch (MathException e) 	{return null;}
    }

    public Ration sub(Ration that){
        if (this.num == 0)
            try {
                return new Ration(-that.num, that.den);
            } catch (MathException e) {
                e.printStackTrace();
            }
        if (that.num == 0)
            return this;
        else try {
            int m = this.den * that.den;
            int z = this.num * that.den - that.num * this.den;
            return new Ration(z, m);
        } catch(MathException e) {
            return null;
        }
    }

    public Ration times(Ration that){
        try {
            return new Ration(this.num * that.num, that.den * this.den);
        } catch (MathException e) {
            return null;
        }
    }

    public Ration divides(Ration that) throws MathException  {
        Ration s = new Ration(that.den, that.num);
        return this.times(s);
    }

    public Ration bacRation() throws MathException {
        return ONE.divides(this);
    }

    public Ration power(int n){
        return power(this,n);
    }

    private Ration power(Ration a,int n) {
        if(a.equals(Ration.ONE))        return a;
        else if(a.equals(Ration.ZERO))   return Ration.ONE;
        else {
            Ration i=power(a,n/2);
            i=i.times(i);
            if(n%2==0) return i;
            else       return a.times(i);
        }
    }

    public double getValue() {
        double mi = this.num / (double) this.den;
        BigDecimal mis = new BigDecimal(Double.toString(mi));
        return mis.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Ration valueOf(String s) throws MathException {
        if(!s.contains("/")) {
            double a = Double.valueOf(s);
            return new Ration(a);
        }
        String[] temp = s.split("/");
        if (temp.length == 1)
            return new Ration(Double.valueOf(temp[0]));
        else
            return new Ration(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]));
    }

    public static Ration valueOf(double number){
        return new Ration(number);
    }

    public static Ration valueOf(int number){
        return new Ration(number);
    }

    public String toString(){
        if 		(this.den == 1)		return  ""+this.num;
        else if (this.den == -1)	return  ""+-this.num;
        else if (this.num == 0)		return  ""+0;
        else if(this.den<0){
            this.num=-this.num;
            this.den=-this.den;
        }
        return ""+num+Ration.SEME+den;
    }

    @Override
    public int compareTo(Ration o) {
        return this.num * o.den - o.num * this.den;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Ration clone() {
        Ration o = null;
        try {
            o = new Ration(this.num, this.den);
        } catch (MathException ignored) {
        }
        return o;
    }

    @Override
    public boolean equals(Object that) {
        if(this == that)
            return true;
        if(that == null)
            return false;
        if(that instanceof Ration) {
            Ration o = (Ration) that;
            if (o.num == this.num)
                if 		(o.num == 0)
                    return true;
                else return o.den == this.den;
        }
        return false;
    }

    @Override
    public int intValue() {
        return this.num/this.den;
    }

    @Override
    public long longValue() {
        return this.num/this.den;
    }

    @Override
    public float floatValue() {
        double mi = this.num / (double) this.den;
        BigDecimal mis = new BigDecimal(Double.toString(mi));
        double s = mis.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return (float) s;
    }

    @Override
    public double doubleValue() {
        return this.floatValue();
    }

    private static int getFactor(int a, int b) {
        int ma, mi;
        if (a > b) {
            ma = a;
            mi = b;
        } else {
            ma = b;
            mi = a;
        }
        int r;
        while ((r = ma % mi) != 0) {
            ma = mi;
            mi = r;
        }
        return mi;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Ration.valueOf("12.234523"));
    }
}

