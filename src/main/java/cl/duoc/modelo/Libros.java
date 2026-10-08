package cl.duoc.modelo;

public class Libros {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private int stock;
    private Categorias id_categoria;


    public Libros(int id, String titulo, String autor, String isbn, String editorial, int stock, Categorias id_categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.id_categoria = id_categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categorias getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categorias id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public String toString() {
        return "Libros: " + '\n' +
                "ID: " + id + '\n' +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor + '\n' +
                "ISBN: " + isbn + '\n' +
                "Editorial: " + editorial + '\n' +
                "Stock: " + stock + '\n' +
                "ID categoria: " + id_categoria;
    }
}
