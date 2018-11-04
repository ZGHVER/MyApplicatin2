package com.example.zgh.myapplication.mathUtil;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Matrix implements
        Serializable, Cloneable, AdjMatrix {
    int line;
    int list;
    Ration[][] arr;

    private Matrix(int i, int j) {
        this.line = i;
        this.list = j;
        if (i != 0 && j != 0)
            arr = new Ration[this.line][this.list];
    }

    Matrix() {
        this(0, 0);
    }

    public Matrix(Ration[][] data) {
        this.line = data.length;
        this.list = data[0].length;
        this.arr = new Ration[this.line][this.list];
        for (int i = 0; i < this.line; i++)
            for (int j = 0; j < this.list; j++)
                this.arr[i][j] = data[i][j].clone();
    }

    public int getRow() {
        return this.line;
    }

    public int getColumn() {
        return this.list;
    }

    public Ration getCell(int row, int column) {
        return arr[row][column];
    }

    public Matrix addM(Matrix other) {
        Matrix o = new Matrix(this.line, other.list);
        for (int i = 0; i < this.line; i++)
            for (int j = 0; j < this.list; j++)
                o.arr[i][j] = this.arr[i][j].adds(other.arr[i][j]);
        return o;
    }

    public Matrix subM(Matrix other) {
        Matrix o = new Matrix(this.line, other.list);
        for (int i = 0; i < this.line; i++)
            for (int j = 0; j < this.list; j++)
                o.arr[i][j] = this.arr[i][j].sub(other.arr[i][j]);
        return o;
    }

    public Matrix timesM(Matrix other) throws ErrorTypeException {
        if (this.list != other.line)
            throw new ErrorTypeException();

        Matrix o = new Matrix(this.line, other.list);
        Ration z = new Ration(0);

        for (int i = 0; i < o.line; i++) {
            for (int j = 0; j < o.list; j++) {
                for (int k = 0; k < this.list; k++)
                    z = z.adds(this.arr[i][k].times(other.arr[k][j]));
                o.arr[i][j] = z;
                z = Ration.ZERO;
            }
        }
        return o;
    }

    public Matrix timesR(Ration k) {
        Matrix o = this.clone();
        for (int i = 0; i < line; i++)
            o.changeLine(i, k);
        return o;
    }

    public Matrix T() {
        Matrix o = new Matrix(this.list, this.line);
        for (int i = 0; i < this.line; i++)
            for (int j = 0; j < this.list; j++)
                o.arr[j][i] = this.arr[i][j].clone();
        return o;
    }

    public Ration getOrder() {
        int souce = line > list ? list : line;
        int R = 0;
        Matrix mb = this.clone();

        for (int i = 0; i < souce; i++) {
            if (mb.arr[i][i].equals(Ration.ZERO))
                continue;
            Ration d;
            try {
                d = Ration.ONE.divides(mb.arr[i][i]);
            } catch (MathException e) {
                d = null;
                e.printStackTrace();
            }
            mb.changeLine(i + 1, d);
            for (int j = i + 1; j < souce; j++)
                mb.changeLine(j + 1, i + 1, mb.arr[j][i].times(Ration.NEGATIVE));
        }
        for (int i = 0; i < this.line; i++) {
            if (mb.isAllZeroOfLine(i))
                R++;
        }
        return new Ration(souce - R);
    }

    public ActMatrix toActMatrix() throws CanNotChangeException {
        return new ActMatrix(this.arr);
    }

    public Determine toDetermine() throws ErrorTypeException {
        return new Determine(this.arr);
    }

    protected void changeLine(int to, int s, int k) {
        this.changeLine(to, s, new Ration(k));
    }

    void changeLine(int to, int s, Ration k) {
        for (int i = 0; i < this.list; i++)
            this.arr[to - 1][i] = this.arr[to - 1][i].adds(this.arr[s - 1][i].times(k));
    }

    protected void changeLine(int to, int k) {
        changeLine(to, Ration.valueOf(k));
    }

    void changeLine(int to, Ration k) {
        for (int i = 0; i < this.list; i++)
            this.arr[to - 1][i] = this.arr[to - 1][i].times(k);
    }

    private boolean isAllZeroOfLine(int indexOfLine) {
        for (int i = 0; i < this.list; i++) {
            if (!this.arr[indexOfLine][i].equals(Ration.ZERO))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.line == 1) {
            s.append("(");
            for (int j = 0; j < this.list; j++) {
                s.append(" ");
                s.append(arr[0][j]);
                s.append("    ");
            }
            s.append(")");
        } else
            for (int i = 0; i < this.line; i++) {
                if (i != 0 && i != this.line - 1)
                    s.append("��");
                else if (i == this.line - 1)
                    s.append("��");
                else if (i == 0)
                    s.append("��");
                for (int j = 0; j < this.list; j++) {
                    s.append(" ");
                    s.append(arr[i][j]);
                    s.append("    ");
                }
                if (i != 0 && i != this.line - 1)
                    s.append("��");
                else if (i == this.line - 1)
                    s.append("��");
                else if (i == 0)
                    s.append("��");
                s.append("\n");
            }
        return s.toString();
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Matrix clone() {
        Matrix o = new Matrix();
        o.line = this.line;
        o.list = this.list;
        o.arr = new Ration[this.line][this.list];
        for (int i = 0; i < this.line; i++)
            for (int j = 0; j < this.list; j++) {
                o.arr[i][j] = this.arr[i][j].clone();
            }
        return o;
    }
}
