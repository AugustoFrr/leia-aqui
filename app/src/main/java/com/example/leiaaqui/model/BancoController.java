package com.example.leiaaqui.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.leiaaqui.R;

public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;
    private ContentValues valores;
    private long resultado;
    private String sucesso, erro = "";
    private Cursor cursor;

    public BancoController(Context context) {
        banco = new CriaBanco(context);
    }

    //Select na tabela de Login
    public Cursor selectLogin(String user) {
        db = banco.getReadableDatabase();
        String[] campos = {CriaBanco.getPASSWORD()};
        String where = CriaBanco.getUSER() + "='" + user + "'";
        cursor = db.query(CriaBanco.getTABELA6(), campos, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Insert e Update na tabela de Cliente
    public String insertCliente(boolean alterar, int id, String nome, String email, String celular, String nascimento,
                                String endereco, String cpf, String categoriaLeitor, Context c) {

        String where = CriaBanco.getID() + "=" + id;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(CriaBanco.getNOME(), nome);
        valores.put(CriaBanco.getEMAIL(), email);
        valores.put(CriaBanco.getCELULAR(), celular);
        valores.put(CriaBanco.getNASCIMENTO(), nascimento);
        valores.put(CriaBanco.getENDERECO(), endereco);
        valores.put(CriaBanco.getCPF(), cpf);
        valores.put(CriaBanco.getCategoriaLeitor(), categoriaLeitor);

        if (alterar) {
            resultado = db.update(CriaBanco.getTABELA(), valores, where, null);
            sucesso = c.getString(R.string.sucessoAlterar);
            erro = c.getString(R.string.erroAlterar);

        } else {
            resultado = db.insert(CriaBanco.getTABELA(), null, valores);
            sucesso = c.getString(R.string.sucessoInserir);
            erro = c.getString(R.string.erroInserir);
        }

        db.close();

        if (resultado == -1)
            return erro;
        else
            return sucesso;
    }

    //Insert e Update na tabela de Categoria de Cliente/Leitor
    public String insertCatCliente(boolean alterar, int id, String codCat, String descCat, String maxDias, String oldCat, Context c) {

        String where = CriaBanco.getID() + "=" + id;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(CriaBanco.getCodCat(), codCat);
        valores.put(CriaBanco.getDescCat(), descCat);
        valores.put(CriaBanco.getMaxDias(), maxDias);

        if (alterar) {
            db.execSQL("UPDATE " + CriaBanco.getTABELA() + " SET categoriaLeitor='" + codCat + "' WHERE " + CriaBanco.getCategoriaLeitor() + " = '" + oldCat + "'");
            resultado = db.update(CriaBanco.getTABELA2(), valores, where, null);
            sucesso = c.getString(R.string.sucessoAlterar);
            erro = c.getString(R.string.erroAlterar);
        } else {
            resultado = db.insert(CriaBanco.getTABELA2(), null, valores);
            sucesso = c.getString(R.string.sucessoInserir);
            erro = c.getString(R.string.erroInserir);
        }
        db.close();

        if (resultado == -1)
            return erro;
        else
            return sucesso;
    }


    //Insert e Update na tabela de Livros
    public String insertLivro(boolean alterar, int id, String codigo, String isbn, String titulo, String codCategoria, String autores,
                              String palavrasChave, String dataPublic, String numEdicao, String editora, String numPags, Context c) {

        String where = CriaBanco.getID() + "=" + id;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(CriaBanco.getCODIGO(), codigo);
        valores.put(CriaBanco.getISBN(), isbn);
        valores.put(CriaBanco.getTITULO(), titulo);
        valores.put(CriaBanco.getCodCategoria(), codCategoria);
        valores.put(CriaBanco.getAUTORES(), autores);
        valores.put(CriaBanco.getPalavrasChave(), palavrasChave);
        valores.put(CriaBanco.getDataPublic(), dataPublic);
        valores.put(CriaBanco.getNumeroEdicao(), numEdicao);
        valores.put(CriaBanco.getEDITORA(), editora);
        valores.put(CriaBanco.getNumPaginas(), numPags);
        valores.put(CriaBanco.getEMPRESTADO(), "false");

        if (alterar) {
            resultado = db.update(CriaBanco.getTABELA3(), valores, where, null);
            sucesso = c.getString(R.string.sucessoAlterar);
            erro = c.getString(R.string.erroAlterar);
        } else {
            resultado = db.insert(CriaBanco.getTABELA3(), null, valores);
            sucesso = c.getString(R.string.sucessoInserir);
            erro = c.getString(R.string.erroInserir);
        }
        db.close();

        if (resultado == -1)
            return erro;
        else
            return sucesso;
    }

    //Insert e Update na tabela de Categoria de Livros
    public String insertCatLivro(boolean alterar, int id, String codCat, String desCat, String maxDias, String taxaMulta, String oldCat, Context c) {

        String where = CriaBanco.getID() + "=" + id;
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        valores.put(CriaBanco.getCodCategoria2(), codCat);
        valores.put(CriaBanco.getDescCategoria(), desCat);
        valores.put(CriaBanco.getMaxDias2(), maxDias);
        valores.put(CriaBanco.getTaxaMulta(), taxaMulta);

        if (alterar) {
            db.execSQL("UPDATE " + CriaBanco.getTABELA3() + " SET codCat='" + codCat + "' WHERE " + CriaBanco.getCodCategoria() + " = '" + oldCat + "'");
            resultado = db.update(CriaBanco.getTABELA4(), valores, where, null);
            sucesso = c.getString(R.string.sucessoAlterar);
            erro = c.getString(R.string.erroAlterar);
        } else {
            resultado = db.insert(CriaBanco.getTABELA4(), null, valores);
            sucesso = c.getString(R.string.sucessoInserir);
            erro = c.getString(R.string.erroInserir);
        }
        db.close();

        if (resultado == -1)
            return erro;
        else
            return sucesso;
    }

    //Insert na tabela de Livros Emprestados
    public String insertEmprestados(String idLivro, String categoria, String nomeCliente, String tituloObra, String dataRetirada,
                                    String dataDevolucao, Context c) {

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.getIdLivro(), idLivro);
        valores.put(CriaBanco.getCategoriaLivro(), categoria);
        valores.put(CriaBanco.getNomeCliente(), nomeCliente);
        valores.put(CriaBanco.getTituloObra(), tituloObra);
        valores.put(CriaBanco.getDataRetirada(), dataRetirada);
        valores.put(CriaBanco.getDataDevolucao(), dataDevolucao);

        resultado = db.insert(CriaBanco.getTABELA5(), null, valores);

        db.execSQL("UPDATE " + CriaBanco.getTABELA3() + " SET emprestado='true' WHERE " + CriaBanco.getID() + " = " + idLivro);

        if (resultado == -1) {
            return c.getString(R.string.erro_emprestado);
        } else {
            return c.getString(R.string.sucesso_emprestado);
        }
    }

    //Select na tabela de Clientes
    public Cursor selectClientes(boolean byId, int id) {
        db = banco.getReadableDatabase();

        if (byId) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getNOME(), CriaBanco.getEMAIL(), CriaBanco.getCELULAR(),
                    CriaBanco.getNASCIMENTO(), CriaBanco.getNASCIMENTO(), CriaBanco.getENDERECO(), CriaBanco.getCPF(), CriaBanco.getCategoriaLeitor()};
            String where = CriaBanco.getID() + "=" + id;
            cursor = db.query(CriaBanco.getTABELA(), campos, where, null, null, null, null, null);
        } else {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCPF(), CriaBanco.getNOME(), CriaBanco.getCategoriaLeitor()};
            cursor = db.query(CriaBanco.getTABELA(), campos, null, null, null, null, null, null);
        }

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    //Select na tabela de Categoria de Clientes/Leitor
    public Cursor selectCatClientes(boolean byId, int id) {
        db = banco.getReadableDatabase();

        if (byId) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCodCat(), CriaBanco.getDescCat(), CriaBanco.getMaxDias()};
            String where = CriaBanco.getID() + "=" + id;
            cursor = db.query(CriaBanco.getTABELA2(), campos, where, null, null, null, null, null);
        } else if (id == -1) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCodCat(), CriaBanco.getDescCat()};
            cursor = db.query(CriaBanco.getTABELA2(), campos, null, null, null, null, null, null);
        } else {
            String[] campos = {CriaBanco.getCodCat()};
            cursor = db.query(CriaBanco.getTABELA2(), campos, null, null, null, null, null, null);
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    //Select na tabela de Livros
    public Cursor selectLivros(boolean byId, int id) {
        db = banco.getReadableDatabase();

        if (byId) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCODIGO(), CriaBanco.getISBN(), CriaBanco.getTITULO(), CriaBanco.getCodCategoria(), CriaBanco.getAUTORES(),
                    CriaBanco.getPalavrasChave(), CriaBanco.getDataPublic(), CriaBanco.getNumeroEdicao(), CriaBanco.getEDITORA(), CriaBanco.getNumPaginas()};
            String where = CriaBanco.getID() + "=" + id;
            cursor = db.query(CriaBanco.getTABELA3(), campos, where, null, null, null, null, null);
        } else {
            String[] campos = {CriaBanco.getID(), CriaBanco.getAUTORES(), CriaBanco.getTITULO(), CriaBanco.getCodCategoria()};
            cursor = db.query(CriaBanco.getTABELA3(), campos, null, null, null, null, null, null);
        }

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    //Select na tabela de Categoria de Livros
    public Cursor selectCatLivros(boolean byId, int id) {
        db = banco.getReadableDatabase();

        if (byId) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCodCategoria2(), CriaBanco.getDescCategoria(), CriaBanco.getMaxDias2(), CriaBanco.getTaxaMulta()};
            String where = CriaBanco.getID() + "=" + id;
            cursor = db.query(CriaBanco.getTABELA4(), campos, where, null, null, null, null, null);
        } else if (id == -1) {
            String[] campos = {CriaBanco.getID(), CriaBanco.getCodCategoria2(), CriaBanco.getDescCategoria()};
            cursor = db.query(CriaBanco.getTABELA4(), campos, null, null, null, null, null, null);
        } else {
            String[] campos = {CriaBanco.getCodCategoria2()};
            cursor = db.query(CriaBanco.getTABELA4(), campos, null, null, null, null, null, null);
        }

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    //Delete em todas as tabelas
    public void deletaRegistro(int tabela, int id) {
        String where = CriaBanco.getID() + "=" + id;
        db = banco.getReadableDatabase();
        switch (tabela) {
            case 1:
                db.delete(CriaBanco.getTABELA(), where, null);
                break;
            case 2:
                db.delete(CriaBanco.getTABELA2(), where, null);
                break;
            case 3:
                db.delete(CriaBanco.getTABELA3(), where, null);
                break;

            case 4:
                db.delete(CriaBanco.getTABELA4(), where, null);
                break;
        }
        db.close();
    }

    //Delete da tabela de Livros Emprestados
    public void devolveLivro(int id) {
        String where = CriaBanco.getIdLivro() + "=" + id;
        db = banco.getWritableDatabase();

        db.delete(CriaBanco.getTABELA5(), where, null);
        db.execSQL("UPDATE " + CriaBanco.getTABELA3() + " SET emprestado='false' WHERE " + CriaBanco.getID() + " = " + id);
    }

    //Pesquisa de Livro por título, autor, editora ou se está emprestado
    public Cursor searchLivro(String keyword, boolean emprestados) {
        db = banco.getReadableDatabase();
        Cursor cursor;

        if (emprestados) {
            cursor = db.rawQuery("select " + CriaBanco.getTituloObra() + ", " + CriaBanco.getNomeCliente() + ", " + CriaBanco.getDataRetirada() + ", " + CriaBanco.getDataDevolucao() + ", " + CriaBanco.getIdLivro() + " from " + CriaBanco.getTABELA5() + " where " +
                    CriaBanco.getTituloObra() + " like ?" + " order by categoria", new String[]{"%" + keyword + "%"});

            if (cursor.moveToFirst()) {
                return cursor;
            } else {
                cursor = db.rawQuery("select " + CriaBanco.getTituloObra() + ", " + CriaBanco.getNomeCliente() + ", " + CriaBanco.getDataRetirada() + ", " + CriaBanco.getDataDevolucao() + ", " + CriaBanco.getIdLivro() + " from " + CriaBanco.getTABELA5() + " where " +
                        CriaBanco.getNomeCliente() + " like ?" + " order by categoria", new String[]{"%" + keyword + "%"});
                if (cursor.moveToFirst()) {
                    return cursor;
                }
            }
        } else {
            cursor = db.rawQuery("select " + CriaBanco.getTITULO() + ", " + CriaBanco.getID() + ", " + CriaBanco.getEMPRESTADO() + " from " + CriaBanco.getTABELA3() + " where " +
                    CriaBanco.getTITULO() + " like ?", new String[]{"%" + keyword + "%"});

            if (cursor.moveToFirst()) {
                return cursor;
            } else {
                cursor = db.rawQuery("select " + CriaBanco.getTITULO() + ", " + CriaBanco.getID() + ", " + CriaBanco.getEMPRESTADO() + " from " + CriaBanco.getTABELA3() + " where " +
                        CriaBanco.getAUTORES() + " like ?", new String[]{"%" + keyword + "%"});
                if (cursor.moveToFirst()) {
                    return cursor;
                } else {
                    cursor = db.rawQuery("select " + CriaBanco.getTITULO() + ", " + CriaBanco.getID() + ", " + CriaBanco.getEMPRESTADO() + " from " + CriaBanco.getTABELA3() + " where " +
                            CriaBanco.getEDITORA() + " like ?", new String[]{"%" + keyword + "%"});
                    if (cursor.moveToFirst()) {
                        return cursor;
                    }
                }
            }
        }
        cursor.close();
        db.close();
        return cursor;
    }

    //Pesquisa de Cliente por nome ou CPF
    public Cursor searchCliente(String keyword) {
        db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + CriaBanco.getNOME() + ", " + CriaBanco.getCPF() + ", " + CriaBanco.getID() + ", " + CriaBanco.getCategoriaLeitor() + " from " + CriaBanco.getTABELA() + " where " +
                CriaBanco.getNOME() + " like ?", new String[]{"%" + keyword + "%"});

        if (cursor.moveToFirst()) {
            return cursor;
        } else {
            cursor = db.rawQuery("select " + CriaBanco.getNOME() + ", " + CriaBanco.getCPF() + ", " + CriaBanco.getID() + ", " + CriaBanco.getCategoriaLeitor() + " from " + CriaBanco.getTABELA() + " where " +
                    CriaBanco.getCPF() + " like ?", new String[]{"%" + keyword + "%"});
            if (cursor.moveToFirst()) {
                return cursor;
            }
        }
        cursor.close();
        db.close();
        return cursor;
    }

    //Checagem de máximo de dias das categorias
    public String selectMaxDias(String nomeCat, boolean cliente) {
        db = banco.getWritableDatabase();
        String retorno = "";
        Cursor cursor;
        if (cliente) {
            String[] campo = {CriaBanco.getMaxDias()};
            String where = CriaBanco.getCodCat() + "='" + nomeCat + "'";
            cursor = db.query(CriaBanco.getTABELA2(), campo, where, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                retorno = cursor.getString(0);
            }

        } else {
            String[] campo = {CriaBanco.getMaxDias2()};
            String where = CriaBanco.getCodCategoria2() + "='" + nomeCat + "'";
            cursor = db.query(CriaBanco.getTABELA4(), campo, where, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                retorno = cursor.getString(0);
            }
        }
        return retorno;
    }

}

