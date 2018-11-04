package com.example.zgh.myapplication.mathUtil;

@SuppressWarnings("unused")
public class ActMatrix extends Matrix {

	public ActMatrix() {
		super();
	}

	public ActMatrix(int k) {
		super.line = super.list = k;
		this.arr = new Ration[k][k];
	}

	public ActMatrix(Ration[][] arr) throws CanNotChangeException {
		if (arr.length == arr[0].length) {
			this.line = this.list = arr.length;
			this.arr = new Ration[this.line][this.line];
			for (int i = 0; i < this.line; i++)
				for (int j = 0; j < this.line; j++) {
                    this.arr[i][j] = arr[i][j].clone();
                }
		} else
			throw new CanNotChangeException();
	}

	private int Remove0(int li, int ls) {
		for (int i = li + 1; i < this.line; i++) {
			this.changeLine(li + 1, i + 1, Ration.ONE);
			if (!this.arr[li][ls].equals(Ration.ZERO))
				return i;
		}
		return -1;
	}

	public ActMatrix getInverse() throws MathException {
        try {
            if (this.toDetermine().getValue().equals(Ration.ZERO))
                throw new MathException();
            else {
                ActMatrix mid = this.clone();
                ActMatrix unit = ActMatrix.getUnitaryMatrix(this.line);
                for (int i = 0; i < this.line; i++) {
                    Ration d;
                    if (mid.arr[i][i].equals(Ration.ZERO)) {
                        int k = mid.Remove0(i, i);
                        unit.changeLine(i + 1, k + 1, Ration.ONE);
                        assert k > 0;
                    }
                    d = Ration.ONE.divides(mid.arr[i][i]);
                    mid.changeLine(i + 1, d);
                    unit.changeLine(i + 1, d);
                    for (int j = 0; j < mid.line; j++) {
                        if (j == i)
                            continue;
                        Ration m = mid.arr[j][i].times(Ration.NEGATIVE);
                        mid.changeLine(j + 1, i + 1, m);
                        unit.changeLine(j + 1, i + 1, m);
                    }
                }
                return unit;
            }
        } catch (ErrorTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

	public Determine toDetermine() throws ErrorTypeException {
        return new Determine(this.arr);

    }

	@Override
    public ActMatrix clone() {
		ActMatrix o = new ActMatrix(this.line);
		for (int i = 0; i < this.line; i++)
			for (int j = 0; j < this.line; j++) {
                o.arr[i][j] = this.arr[i][j].clone();
            }
		return o;
	}

	public static ActMatrix getUnitaryMatrix(int r) {
		int s = 0;
		ActMatrix o = new ActMatrix(r);
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < r; j++)
				if (j == s)
					o.arr[i][j] = Ration.ONE.clone();
				else
					o.arr[i][j] = Ration.ZERO.clone();
			s++;
		}
		return o;
	}

	public static void main(String[] args) {
		ActMatrix a = new ActMatrix();
        try {
			System.out.println(a.timesM(a.getInverse()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}