package com.example.zgh.myapplication.mathUtil;


public class Determine implements Cloneable { // ����ʽ
	private int area;
	private Ration[][] arr;

	public Determine(int k) {
		if ((this.area = k) != 0)
			arr = new Ration[k][k];
	}

	public Determine(Ration[][] aars) throws ErrorTypeException {
		this(aars.length);
		if (aars.length == aars[0].length) {
			for (int i = 0; i < aars.length; i++)
				for (int j = 0; j < aars.length; j++)
					this.arr[i][j] = aars[i][j].clone();
		} else
			throw new ErrorTypeException();
	}

	public Determine() {
		this(0);
	}

	private Ration MultipTo1(int line, int list) {
		if (this.arr[line][list].equals(Ration.ZERO)) {
			for (int i = line + 1; i < this.area; i++) {
				this.ChangeLineDete(line + 1, i + 1, Ration.ONE);
				if (!this.arr[line][list].equals(Ration.ZERO))
					break;
			}
		}
		Ration d;
		try {
			d = Ration.ONE.divides(this.arr[line][list]);
		} catch (MathException e) {
			d = Ration.ZERO;
		}
		for (int i = 0; i < this.area; i++)
			this.arr[line][i] = this.arr[line][i].times(d);
		return d;
	}

	private void ChangeLineDete(int to, int m, Ration k) {
		to -= 1;
		m -= 1;
		for (int i = 0; i < this.area; i++)
			this.arr[to][i] = this.arr[to][i].adds((this.arr[m][i].times(k)));
	}

	public void NubMiltie(int k, int to) {
		for (int i = 0; i < this.area; i++)
			this.arr[to][i] = this.arr[to][i].times(Ration.valueOf(k));
	}

	public Ration getValue() {
		Determine teg = this.clone();
		Ration value = Ration.ONE;
		for (int i = 0; i < this.area; i++) {
			value = value.times(teg.MultipTo1(i, i));
			for (int j = i + 1; j < this.area; j++) {
				Ration temp = teg.arr[j][i].times(Ration.NEGATIVE);
				teg.ChangeLineDete(j + 1, i + 1, temp);
			}
		}
		for (int i = 0; i < teg.area - 1; i++) {
			if (!teg.arr[teg.area - 1][i].equals(Ration.ZERO))
				return Ration.ZERO;
		}

		try {
			return Ration.ONE.divides(value);
		} catch (MathException e) {
			return Ration.ZERO;
		}
	}

	public Determine clone() {
		Determine o = new Determine(this.area);
		for (int i = 0; i < this.area; i++)
			for (int j = 0; j < this.area; j++)
				o.arr[i][j] = this.arr[i][j].clone();
		return o;
	}

	public Ration[][] getArr(){
	    return this.arr;
    }

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.area; i++) {
			s.append("��");
			for (int j = 0; j < this.area; j++) {
				s.append(" ").append(this.arr[i][j]).append(" ");
			}
			s.append("��" + "\n");
		}
		return s.toString();
	}

}
