package com.example.Entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Principal {
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand;
    int compOrd,movOrd,compRev,compRand,movRev,movRand;
    Long tini, tfim, totalOrd,totalRev,totalRand;
    RandomAccessFile arq;

    private void cabecalho() {
        String colunas = """
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n
                |    Metodos Ord     |              Arquivo ordenado              |          Arquivo em ordem reversa          |              Arquivo randomico             |\n
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n
                |                    |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |  Comp. |  Comp. |  mov.  |  mov.  | Tempo  |\n
                |                    |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |  Prog* |  equa# |  Prog* |  equa# | (Seg)  |\n
                |--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|
                """;
        gravarArq(colunas);
    }

    private void gravaLinhaCompleta() {
        String linha="|--------------------|--------------------------------------------|--------------------------------------------|--------------------------------------------|\n";
        gravarArq(linha);
    }

    private String centraliza(int num) {
        String registro="",valor=""+num;
        int qtd=(8-valor.length())/2;
        for (int i = 0; i < qtd; i++)
            registro+=" ";
        registro+=valor;
        while (registro.length()<8)
            registro+=" ";
        return registro;
    }

    public void gravarArq(String linha){
        try{
            arq.writeBytes(linha);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void gravaNomeMetodo(String nome){
        int tamanho= nome.length();
        String linha="|";
        for (int i = 0; i < (20-tamanho)/2; i++) {
            linha+=" ";
        }
        linha+=nome;
        while (linha.length()<21) {
            linha+=" ";
        }
        linha+="|";
        gravarArq(linha);
    }

    public void gravaLinhaTabela(int comp,int compCalculado, int mov, int movCalculado, int tempo, boolean finalLinha){
        String reg="",linha="";
        reg=centraliza(comp);
        reg+="|";
        linha+=reg;
        reg=centraliza(compCalculado);
        reg+="|";
        linha+=reg;
        reg=centraliza(mov);
        reg+="|";
        linha+=reg;
        reg=centraliza(movCalculado);
        reg+="|";
        linha+=reg;
        reg=centraliza(tempo);
        reg+="|";
        linha+=reg;
        if (finalLinha){
            linha+="\n";
            gravarArq(linha);
            gravaLinhaCompleta();
        }
        else
            gravarArq(linha);
    }

    public void gerarTabela() throws IOException {
        try {
            arq = new RandomAccessFile("src/tabela.txt", "rw");
            arq.setLength(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cabecalho();
        arqOrd= new Arquivo("Ordenado");
        arqRand = new Arquivo("Randomico");
        arqRev = new Arquivo("Reverso");
        auxRand = new Arquivo("AuxRand");
        auxRev = new Arquivo("AuxRev");
        arqOrd.geraArquivoOrdenado(1024);
        arqRand.geraArquivoRandomico(1024);
        arqRev.geraArquivoReverso(1024);
        int tl = arqOrd.filesize();

        //INSERÇÃO DIRETA
        gravaNomeMetodo("Inserção Direta");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.insertionSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,tl-1,movOrd,3*(tl-1),(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.insertionSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,(tl*tl+tl-4)/4,movRev,(tl*tl+3*tl-4)/2,(int)(totalOrd/1000),false);
        //
         auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.insertionSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,(tl*tl+tl-2)/4,movRand,(tl*tl+9*tl-10)/4,(int)(totalOrd/1000),true);



        //Inserção Binaria
        gravaNomeMetodo("Insercao binaria");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.insercaobinaria();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,tl*((int)(Math.log(tl)-Math.log(Math.E)+0.5)),movOrd,3*(tl-1),(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.insercaobinaria();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,tl*((int)(Math.log(tl)-Math.log(Math.E)+0.5)),movRev,(tl*tl+3*tl-4)/2,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.insercaobinaria();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,tl*((int)(Math.log(tl)-Math.log(Math.E)+0.5)),movRand,(tl*tl+9*tl-10)/4,(int)(totalOrd/1000),true);


        //Selecao Direta
        gravaNomeMetodo("Selecao Direta");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.selectionsort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,(tl*tl-tl)/2,movOrd,3*(tl-1),(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.selectionsort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,(tl*tl-tl)/2,movRev,(tl*tl)/4+3*(tl-1),(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.selectionsort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,(tl*tl-tl)/2,movRand,tl*((int)((Math.log(tl)+0.577216))),(int)(totalOrd/1000),true);

        //Bolha
        gravaNomeMetodo("Bubble Sort");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.BubbleSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,(tl*tl-tl)/2,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.BubbleSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,(tl*tl-tl)/2,movRev,3*(tl*tl-tl)/4,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.BubbleSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,(tl*tl-tl)/2,movRand,3*(tl*tl-tl)/2,(int)(totalOrd/1000),true);



        //Shake
        gravaNomeMetodo("Shake Sort");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.shakeSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,(tl*tl-tl)/2,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.shakeSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,(tl*tl-tl)/2,movRev,3*(tl*tl-tl)/4,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.shakeSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,(tl*tl-tl)/2,movRand,3*(tl*tl-tl)/2,(int)(totalOrd/1000),true);



        //Shell
        gravaNomeMetodo("Shell");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.shellSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.shellSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.shellSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Heap
        gravaNomeMetodo("Heap");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.heapSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.heapSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.heapSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);

        //Quick
        gravaNomeMetodo("Quick Pivo");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.QuickPivo();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.QuickPivo();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.QuickPivo();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Quick sem pivo
        gravaNomeMetodo("Quick S/Pivo");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.QuickSemP();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.QuickSemP();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.QuickSemP();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Merge 1
        gravaNomeMetodo("Merge 1");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.Merge1();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.Merge1();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.Merge1();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Merge 2
        gravaNomeMetodo("Merge 2");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.MergeSort2();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.MergeSort2();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.MergeSort2();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Couting
        gravaNomeMetodo("Couting");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.CoutingSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.CoutingSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.CoutingSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Bucket
        gravaNomeMetodo("Bucket");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.BucketSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.BucketSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.BucketSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Radix
        gravaNomeMetodo("Radix");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.RadixSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.RadixSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.RadixSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);


        //Comb sort
        gravaNomeMetodo("Comb sort");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.CombSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.CombSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.CombSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);

        //Gnome
        gravaNomeMetodo("Gnome");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.GnomeSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.GnomeSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.GnomeSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);

        //Tim
        gravaNomeMetodo("Tim");
        //Ordenado
        arqOrd.initMov();
        arqOrd.initComp();
        tini= System.currentTimeMillis();
        arqOrd.TimSort();
        tfim = System.currentTimeMillis();
        compOrd = arqOrd.getComp();
        movOrd = arqOrd.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compOrd,0,movOrd,0,(int)(totalOrd/1000),false);
        //
        auxRev.copiaArquivo(arqRev.getFile());// copiamos o arquivo reverso para nao gerar um novo tova vez
        //Ordem reversa
        auxRev.initMov();
        auxRev.initComp();
        tini= System.currentTimeMillis();
        auxRev.TimSort();
        tfim = System.currentTimeMillis();
        compRev = auxRev.getComp();
        movRev = auxRev.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRev,0,movRev,0,(int)(totalOrd/1000),false);
        //
        auxRand.copiaArquivo(arqRand.getFile());
        //Ordem randomica
        auxRand.initMov();
        auxRand.initComp();
        tini= System.currentTimeMillis();
        auxRand.TimSort();
        tfim = System.currentTimeMillis();
        compRand = auxRand.getComp();
        movRand = auxRand.getMov();
        totalOrd = tfim-tini;
        gravaLinhaTabela(compRand,0,movRand,0,(int)(totalOrd/1000),true);

    }

}
