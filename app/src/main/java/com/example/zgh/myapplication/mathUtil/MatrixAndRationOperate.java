package com.example.zgh.myapplication.mathUtil;

import android.view.View;
import com.example.zgh.myapplication.calculTool.BinOperator;
import com.example.zgh.myapplication.calculTool.UnaOperator;

public class MatrixAndRationOperate {
    public static final BinOperator<AdjMatrix> Add;
    static{
        Add = new BinOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operate(AdjMatrix a, AdjMatrix b) throws ErrorTypeException {
                if (a instanceof Matrix) {
                    if (b instanceof Matrix) {
                        return ((Matrix) a).addM((Matrix) b);
                    }
                } else if (b instanceof Ration)
                    return ((Ration) a).adds((Ration) b);
                throw new ErrorTypeException("数字无法与矩阵相加");
            }
        };
    }
    public static final BinOperator<AdjMatrix> Sub;
    static{
        Sub = new BinOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operate(AdjMatrix a, AdjMatrix b) throws ErrorTypeException {
                if (a instanceof Matrix) {
                    if (b instanceof Matrix) {
                        return ((Matrix) a).subM((Matrix) b);
                    }
                } else if (b instanceof Ration)
                    return ((Ration) a).sub((Ration) b);
                throw new ErrorTypeException("数字无法与矩阵相减");
            }
        };
    }
    public static final BinOperator<AdjMatrix> Tim;
    static{
        Tim = new BinOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operate(AdjMatrix a, AdjMatrix b) throws ErrorTypeException {
                if (a instanceof Matrix) {
                    if (b instanceof Matrix) {
                        try {
                            return ((Matrix) a).timesM((Matrix) b);
                        } catch (ErrorTypeException e) {
                            throw new ErrorTypeException("矩阵不满足乘法规则");
                        }
                    } else {
                        return ((Matrix) a).timesR((Ration) b);
                    }
                } else {
                    if (b instanceof Matrix)
                        return ((Matrix) b).timesR((Ration) a);
                    else
                        return ((Ration) b).times((Ration) a);
                }
            }
        };
    }
    public static final BinOperator<AdjMatrix> Div;
    static{
        Div = new BinOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operate(AdjMatrix a, AdjMatrix b) throws ErrorTypeException {
                try {
                    if (a instanceof Matrix) {
                        a = ((Matrix) a).toActMatrix();
                        if (b instanceof Matrix) {
                            b = ((Matrix) b).toActMatrix();
                            return ((ActMatrix) a).timesM(((ActMatrix) b).getInverse());
                        } else {
                            return ((ActMatrix) a).timesR(((Ration) b).bacRation());
                        }
                    } else {
                        if (b instanceof Ration)
                            return ((Ration) a).divides((Ration) b);
                    }
                } catch (ErrorTypeException e) {
                    throw new ErrorTypeException("矩阵行列不满足乘法规则");
                } catch (CanNotChangeException e) {
                    throw new ErrorTypeException("不是方阵，没有逆，无法进行除法运算");
                } catch (MathException e) {
                    throw new ErrorTypeException("方阵的值为0，无逆运算");
                }
                throw new ErrorTypeException("数字无法与矩阵相除");
            }
        };
    }
    public static final UnaOperator<AdjMatrix> R_A_;
    static{
        R_A_ = new UnaOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operator(AdjMatrix a) throws ErrorTypeException {
                if (a instanceof Matrix) {
                    return ((Matrix) a).getOrder();
                }
                throw new ErrorTypeException("只有矩阵才有秩");
            }
        };
    }
    public static final UnaOperator<AdjMatrix> V_A_;
    static{
        V_A_ = new UnaOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operator(AdjMatrix a) throws ErrorTypeException {
                if (a instanceof Matrix) {
                    try {
                        return ((Matrix) a).toDetermine().getValue();
                    } catch (ErrorTypeException ignored) {}
                }
                throw new ErrorTypeException("只有方阵才有值");
            }
        };
    }
    public static final UnaOperator<AdjMatrix> N_A_;
    static{
        N_A_ = new UnaOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operator(AdjMatrix a) throws ErrorTypeException {
                if(a instanceof Matrix) {
                    try {
                        return ((Matrix)a).toActMatrix().getInverse();
                    } catch (MathException e) {
                        throw new ErrorTypeException("矩阵的值为0，没有逆");
                    } catch (CanNotChangeException ignored) {}
                }
                throw new ErrorTypeException("方阵才有逆");
            }
        };
    }
    public static final UnaOperator<AdjMatrix> T_A_;
    static{
        T_A_ =  new UnaOperator<AdjMatrix>() {
            @Override
            public AdjMatrix operator(AdjMatrix a) throws ErrorTypeException {
                if(a instanceof Matrix)
                    return ((Matrix) a).T();
                throw new ErrorTypeException("矩阵才能转置");
            }
        };
    }
}
