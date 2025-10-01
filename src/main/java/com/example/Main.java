package com.example;

import com.example.Entity.Arquivo;
import com.example.Entity.Lista;
import com.example.Entity.Principal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
    public static void main(String[] args) throws IOException {
        //Principal tabela = new Principal();
       // tabela.gerarTabela();
       /* Lista lista = new Lista();
        lista.gerarNumerosseq(100);
       //lista.inserirnaLista(2);
        //lista.inserirnaLista(5);
       //lista.inserirnaLista(1);
        // lista.inserirnaLista(8);
        //lista.inserirnaLista(10);
        //lista.inserirnaLista(3);
        //lista.inserirnaLista(4);
        //lista.inserirnaLista(6);
        //lista.inserirnaLista(7);
        //lista.inserirnaLista(15);
        //lista.inserirnaLista(9);
        //lista.inserirnaLista(11);
        //lista.inserirnaLista(16);
        //lista.inserirnaLista(5000);
        //lista.inserirnaLista(14);
        //lista.inserirnaLista(0);
        //lista.inserirnaLista(12);
        System.out.println("Lista ordenada ");
        //lista.merge1();
       // lista.MergeSort2();
        //lista.QuickPivo();
        lista.QuickSemPivo();
        lista.exibir();

        //lista.insertionSort();
        //lista.selectionSort();
        //lista.insertionBinary();
        //lista.BubbleSort();
        //lista.HeapSort();
        //lista.ShakeSort();
        //lista.ShellSort();
        //lista.bucketSort();
        //lista.radixSort();
        //lista.GnomeSort();
        //lista.CombSort();
        //lista.exibir();
        //lista.TimSort();
        //lista.exibir();
        //lista.QuickPivo();
        //lista.exibir();

        */


       Arquivo arquivorand = new Arquivo("ArqRandom");
//        arquivorand.geraArquivoRandomico(8);
         //arquivorand.geraArquivoReverso(150);
        arquivorand.geraArquivoTeste();
        //arquivorand.Exibe();

        //arquivorand.Exibe();
        //arquivorand.insertionSort();
        //arquivorand.BubbleSort();
        //arquivorand.insercaobinaria();
        //arquivorand.selectionsort();
        //arquivorand.shakeSort();
        //arquivorand.heapSort();
        //arquivorand.shellSort();
        //arquivorand.GnomeSort();
        //arquivorand.CombSort();
        //arquivorand.BucketSort();
        //arquivorand.CoutingSort();
        //arquivorand.RadixSort();
        //arquivorand.QuickPivo();
        //arquivorand.TimSort();
        //arquivorand.shellSort();
//        arquivorand.MergeSort2();
        arquivorand.QuickPivo();
        //arquivorand.QuickSemP();
        // arquivorand.Merge1();
        //arquivorand.QuickPivo();
        //System.out.println("lista ordenada");
       arquivorand.Exibe();
//        arquivorand.ExcluirArq();

    }
}