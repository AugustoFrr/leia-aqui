package com.example.leiaaqui.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    private static final int VERSAO = 1;

    //TABELA CLIENTES
    private static final String TABELA = "clientes";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String CELULAR = "celular";
    private static final String NASCIMENTO = "nascimento";
    private static final String ENDERECO = "endereco";
    private static final String CPF = "cpf";
    private static final String CATEGORIA_LEITOR = "categoriaLeitor";

    //TABELA CATEGORIA DE LEITORES
    private static final String TABELA2 = "catClientes";
    private static final String COD_CAT = "codCategoria";
    private static final String DESC_CAT = "descCategoria";
    private static final String MAX_DIAS = "maxDias";

    //TABELA LIVROS
    private static final String TABELA3 = "livros";
    private static final String CODIGO = "codigo";
    private static final String ISBN = "isbn";
    private static final String TITULO = "titulo";
    private static final String COD_CATEGORIA = "codCat";
    private static final String AUTORES = "autores";
    private static final String PALAVRAS_CHAVE = "palavrasChave";
    private static final String DATA_PUBLIC = "dataPublicacao";
    private static final String NUMERO_EDICAO = "numEdicao";
    private static final String EDITORA = "editora";
    private static final String NUM_PAGINAS = "numPaginas";
    private static final String EMPRESTADO = "emprestado";

    //TABELA CATEGORIA DE LIVROS
    private static final String TABELA4 = "catLivros";
    private static final String COD_CATEGORIA2 = "codCategoria";
    private static final String DESC_CATEGORIA = "descCategoria";
    private static final String MAX_DIAS2 = "maxDias";
    private static final String TAXA_MULTA = "taxaMulta";

    //TABELA LIVROS EMPRESTADOS
    private static final String TABELA5 = "livrosEmprestados";
    private static final String ID_LIVRO = "idLivro";
    private static final String CATEGORIA_LIVRO = "categoria";
    private static final String NOME_CLIENTE = "nomeCliente";
    private static final String TITULO_OBRA = "tituloObra";
    private static final String DATA_RETIRADA = "dataRetirada";
    private static final String DATA_DEVOLUCAO = "dataDevolucao";

    //TABELA LOGIN
    private static final String TABELA6 = "login";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public CriaBanco(Context context) {
        super(context, getNomeBanco(), null, getVERSAO());
    }

    //MÉTODOS GETS
    public static String getNomeBanco() {
        return NOME_BANCO;
    }

    public static int getVERSAO() {
        return VERSAO;
    }

    public static String getTABELA() {
        return TABELA;
    }

    public static String getNOME() {
        return NOME;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getCELULAR() {
        return CELULAR;
    }

    public static String getNASCIMENTO() {
        return NASCIMENTO;
    }

    public static String getENDERECO() {
        return ENDERECO;
    }

    public static String getCPF() {
        return CPF;
    }

    public static String getCategoriaLeitor() {
        return CATEGORIA_LEITOR;
    }

    public static String getTABELA2() {
        return TABELA2;
    }

    public static String getCodCat() {
        return COD_CAT;
    }

    public static String getDescCat() {
        return DESC_CAT;
    }

    public static String getMaxDias() {
        return MAX_DIAS;
    }

    public static String getTABELA3() {
        return TABELA3;
    }

    public static String getCODIGO() {
        return CODIGO;
    }

    public static String getISBN() {
        return ISBN;
    }

    public static String getTITULO() {
        return TITULO;
    }

    public static String getCodCategoria() {
        return COD_CATEGORIA;
    }

    public static String getAUTORES() {
        return AUTORES;
    }

    public static String getPalavrasChave() {
        return PALAVRAS_CHAVE;
    }

    public static String getDataPublic() {
        return DATA_PUBLIC;
    }

    public static String getNumeroEdicao() {
        return NUMERO_EDICAO;
    }

    public static String getEDITORA() {
        return EDITORA;
    }

    public static String getNumPaginas() {
        return NUM_PAGINAS;
    }

    public static String getTABELA4() {
        return TABELA4;
    }

    public static String getCodCategoria2() {
        return COD_CATEGORIA2;
    }

    public static String getDescCategoria() {
        return DESC_CATEGORIA;
    }

    public static String getMaxDias2() {
        return MAX_DIAS2;
    }

    public static String getTaxaMulta() {
        return TAXA_MULTA;
    }

    public static String getID() {
        return ID;
    }

    public static String getTABELA5() {
        return TABELA5;
    }

    public static String getCategoriaLivro() {
        return CATEGORIA_LIVRO;
    }

    public static String getNomeCliente() {
        return NOME_CLIENTE;
    }

    public static String getTituloObra() {
        return TITULO_OBRA;
    }

    public static String getDataRetirada() {
        return DATA_RETIRADA;
    }

    public static String getDataDevolucao() {
        return DATA_DEVOLUCAO;
    }

    public static String getIdLivro() {
        return ID_LIVRO;
    }

    public static String getEMPRESTADO() {
        return EMPRESTADO;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getTABELA6() {
        return TABELA6;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String criarTabela1 = "CREATE TABLE " + TABELA + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOME + " text,"
                + EMAIL + " text,"
                + CELULAR + " text,"
                + NASCIMENTO + " text,"
                + ENDERECO + " text,"
                + CPF + " text,"
                + CATEGORIA_LEITOR + " text"
                + ")";

        String criarTabela2 = "CREATE TABLE " + TABELA2 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COD_CAT + " text,"
                + DESC_CAT + " text,"
                + MAX_DIAS + " text"
                + ")";

        String criarTabela3 = "CREATE TABLE " + TABELA3 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CODIGO + " text,"
                + ISBN + " text,"
                + TITULO + " text,"
                + COD_CATEGORIA + " text,"
                + AUTORES + " text,"
                + PALAVRAS_CHAVE + " text,"
                + DATA_PUBLIC + " text,"
                + NUMERO_EDICAO + " text,"
                + EDITORA + " text,"
                + NUM_PAGINAS + " text,"
                + EMPRESTADO + " text"
                + ")";

        String criarTabela4 = "CREATE TABLE " + TABELA4 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COD_CATEGORIA2 + " text,"
                + DESC_CATEGORIA + " text,"
                + MAX_DIAS2 + " text,"
                + TAXA_MULTA + " text"
                + ")";

        String criarTabela5 = "CREATE TABLE " + TABELA5 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ID_LIVRO + " text,"
                + CATEGORIA_LIVRO + " text,"
                + NOME_CLIENTE + " text,"
                + TITULO_OBRA + " text,"
                + DATA_RETIRADA + " text,"
                + DATA_DEVOLUCAO + " text"
                + ")";

        String criarTabela6 = "CREATE TABLE " + TABELA6 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER + " text,"
                + PASSWORD + " text"
                + ")";

        sqLiteDatabase.execSQL(criarTabela1);
        sqLiteDatabase.execSQL(criarTabela2);
        sqLiteDatabase.execSQL(criarTabela3);
        sqLiteDatabase.execSQL(criarTabela4);
        sqLiteDatabase.execSQL(criarTabela5);
        sqLiteDatabase.execSQL(criarTabela6);

        //INSERÇÕES DE EXEMPLO
        sqLiteDatabase.execSQL("INSERT INTO login('user', 'password') VALUES('user', '12345')");

        sqLiteDatabase.execSQL("INSERT INTO catClientes('codCategoria', 'descCategoria', 'maxDias') VALUES " +
                "('Estudante EM', 'Ensino Médio', '5'), " +
                "('Estudante ES', 'Ensino Superior', '7'), " +
                "('Professor EM', 'Ensino Médio', '12'), " +
                "('Professor ES', 'Ensino Superior', '14')");

        sqLiteDatabase.execSQL("INSERT INTO catLivros('codCategoria', 'descCategoria', 'maxDias', 'taxaMulta') VALUES " +
                "('Livro', 'Livros diversos', '14', '10,00'), " +
                "('Revista', 'Revistas diversas', '12', '5,00')," +
                "('Mangá', 'Mangás diversos', '7', '5,00')," +
                "('Jornal', 'Jornais diversos', '5', '3,00') ");

        sqLiteDatabase.execSQL("INSERT INTO clientes('nome', 'email', 'celular', 'nascimento', 'endereco', 'cpf', 'categoriaLeitor') VALUES" +
                "('Augusto Ferreira', 'augusto@gmail.com', '11 91234-5678', '01/01/2019', 'Rua Casa do Ator', '111.111.111-11', 'Estudante ES'), " +
                "('Caio Souza', 'caio@gmail.com', '11 91234-5678', '02/02/2019', 'Rua Casa do Ator', '222.222.222-22', 'Estudante ES'), " +
                "('Guilherme Alves', 'guilherme@gmail.com', '11 91234-5678', '03/03/2019', 'Rua Casa do Ator', '333.333.333-33', 'Estudante ES'), " +
                "('Nathalia Sousa', 'nathalia@gmail.com', '11 91234-5678', '04/04/2019', 'Rua Casa do Ator', '444.444.444-44', 'Estudante ES'), " +
                "('Maricélia Soares', 'maricelia@gmail.com', '11 91234-5678', '05/05/2019', 'Rua Casa do Ator', '555.555.555-55', 'Professor ES')");

        sqLiteDatabase.execSQL("INSERT INTO livros('codigo', 'isbn', 'titulo', 'autores', 'palavrasChave', 'dataPublicacao', 'numEdicao', 'editora', 'numPaginas', 'codCat', 'emprestado') VALUES " +
                "('L25451', '978-3-16-148410-0', 'Harry Potter e a Pedra Filosofal', 'JK Rowling', 'fantasia, magia', '01/01/2000', '1º', 'Bloomsbury', '100', 'Livro', 'false'), " +
                "('L39874', '978-3-16-148410-0', 'Percy Jackson e o Ladrão de Raios', 'Rick Riordan', 'fantasia, magia', '01/01/2000', '1º', 'Miramax Books', '100', 'Livro', 'false'), " +
                "('M89933', '978-3-16-148410-0', 'Naruto Shippudden Vol. 13', 'Masashi Kishimoto', 'shounen,ação', '01/01/2000', '1º', 'Shounen Jump', '100', 'Mangá', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Harry Potter e a Câmara Secreta', 'JK Rowling', 'fantasia, magia', '01/01/2000', '1º', 'Bloomsbury', '100', 'Livro', 'false'), " +
                "('M25451', '978-3-16-148410-0', 'Jogos Vorazes', 'Suzanne Collins', 'mistério, fantasia', '01/01/2000', '1º', 'Rocco', '100', 'Livro', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Percy Jackson e o Mar de Monstros', 'Rick Riordan', 'fantasia, magia', '01/01/2000', '1º', 'Miramax Books', '100', 'Livro', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Harry Potter e o Prisioneiro de Azkaban', 'JK Rowling', 'fantasia, magia', '01/01/2000', '1º', 'Bloomsbury', '100', 'Livro', 'false'), " +
                "('M89933', '978-3-16-148410-0', 'Naruto Shippudden Vol. 45', 'Masashi Kishimoto', 'shounen,ação', '01/01/2000', '1º', 'Shounen Jump', '100', 'Mangá', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Dom Casmurro', 'Machado de Assis', 'Romance, Realismo', '01/01/2000', '1º', 'Livraria Garnier', '100', 'Livro', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Harry Potter e o Cálice de Fogo', 'JK Rowling', 'fantasia, magia', '01/01/2000', '1º', 'Bloomsbury', '100', 'Livro', 'false'), " +
                "('M89933', '978-3-16-148410-0', 'Naruto Shippudden Vol. 27', 'Masashi Kishimoto', 'shounen,ação', '01/01/2000', '1º', 'Shounen Jump', '100', 'Mangá', 'false'), " +
                "('L25451', '978-3-16-148410-0', 'Harry Potter e a Ordem da Fênix', 'JK Rowling', 'fantasia, magia', '01/01/2000', '1º', 'Bloomsbury', '100', 'Livro', 'false'), " +
                "('M89933', '978-3-16-148410-0', 'Demon Slayer Vol. 1', 'Koyoharu Gotouge', 'shounen,ação', '01/01/2000', '1º', 'Shounen Jump', '100', 'Mangá', 'false')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA5);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABELA6);

        onCreate(sqLiteDatabase);
    }
}