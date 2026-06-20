package model;

// Interface do observado (Cap.17). O Model implementa para notificar a View.
public interface Observado {
    void adicionarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores();
}
