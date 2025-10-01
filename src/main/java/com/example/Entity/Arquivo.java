package com.example.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Arquivo {
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo(String nomearquivo) throws FileNotFoundException {
        this.nomearquivo = nomearquivo;
        arquivo = new RandomAccessFile(nomearquivo, "rw");
    }


    public void copiaArquivo(RandomAccessFile arquivoOrigem)
    {
        Registro reg = new Registro();
        try{
            arquivoOrigem.seek(0);
            arquivo.seek(0);
            truncate(0);
            while(arquivoOrigem.getFilePointer() < arquivoOrigem.length()){
                reg.leDoArq(arquivoOrigem);
                reg.gravaNoArq(arquivo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public RandomAccessFile getFile() {
        return arquivo;
    }

    public void truncate(long pos) {
        try{
            arquivo.setLength(pos * Registro.length());
        }catch (IOException ioException){

        }
    }
    public boolean eof() {
        try{
            if(arquivo.getFilePointer() == arquivo.length()){
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {

        }


    }
    public int filesize(){
        try {
            return (int) (arquivo.length() / Registro.length());
        } catch (IOException e) {

        }
        return -1;
    }

    public void initComp() {
        comp = 0;
    }
    public void initMov() {
        mov= 0;
    }
    public int getComp() {
        return comp;
    }
    public int getMov() {
        return mov;
    }

    public void BubbleSort() {
        Registro regi = new Registro();
        Registro regj = new Registro();
        boolean flag = true;
        int i = filesize();
        int j;
        while(i>0 && flag){ // lidar com excluir o mais pesado
            j =0;
            flag = false;
            while(j<i-1){
                seekArq(j);
                regi.leDoArq(arquivo);
                regj.leDoArq(arquivo);
                comp++;
                if(regi.getNumero()>regj.getNumero()){
                    seekArq(j);
                    regj.gravaNoArq(arquivo);
                    regi.gravaNoArq(arquivo);
                    flag = true;
                    mov+=2;
                }
                j++;
            }
            i--;
        }
    }
    public void insertionSort(){
        int tl = filesize();
        int pos =1;
        Registro regi = new Registro(), regj = new Registro();
        int i;
        while(pos<tl){
            i= pos;
            seekArq(i-1);
            regj.leDoArq(arquivo);
            regi.leDoArq(arquivo);
            comp++;
            while(i>0 && regj.getNumero()>regi.getNumero()){
                seekArq(i);
                regj.gravaNoArq(arquivo);
                mov++;
                i--;
                if(i>0){
                    seekArq(i-1);
                    regj.leDoArq(arquivo);
                }
                comp++;
            }
            seekArq(i);
            regi.gravaNoArq(arquivo);
            mov++;
            pos++;
        }
    }

    public int buscaBinaria(int chave,int tl){
        int inicio = 0, fim = tl-1, meio = (inicio+fim)/2;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);
        while(inicio<fim){
            comp++;
            if(chave>reg.getNumero())
                inicio=meio+1;
            else
                fim = meio-1;

            meio = (inicio+fim)/2;
            seekArq(meio);
            reg.leDoArq(arquivo);
        }
        comp++;
        if(chave>reg.getNumero())
                return meio+1;
        return meio;
    }

    public void insercaobinaria(){
        Registro reg = new Registro();
        Registro aux =new Registro();
        int tl = filesize();
        int pos;
        for (int i = 1; i < tl; i++) {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = buscaBinaria(reg.getNumero(), i);
            for(int j = i;j>pos;j--){
                seekArq(j-1);
                aux.leDoArq(arquivo);
                aux.gravaNoArq(arquivo);
                mov++;
            }
            seekArq(pos);
            reg.gravaNoArq(arquivo);
            mov++;
        }
    }
    public void shakeSort(){
        Registro regi = new Registro();
        Registro regj = new Registro();
        boolean flag = true;
        int fim = filesize();
        int j;
        int inicio=0;
        int i;

        while(inicio<fim && flag){ // lidar com excluir o mais pesado
            j =inicio;
            flag = false;
            while(j<fim-1){
                seekArq(j);
                regi.leDoArq(arquivo);
                regj.leDoArq(arquivo);
                comp++;
                if(regi.getNumero()>regj.getNumero()){
                    seekArq(j);
                    regj.gravaNoArq(arquivo);
                    regi.gravaNoArq(arquivo);
                    flag = true;
                    mov+=2;
                }
                j++;
            }
            fim--;
            i=fim;
            while(i>inicio){
                seekArq(i-1);
                regi.leDoArq(arquivo);
                regj.leDoArq(arquivo);
                comp++;
                if(regj.getNumero()<regi.getNumero()){
                    seekArq(i-1);
                    regj.gravaNoArq(arquivo);
                    regi.gravaNoArq(arquivo);
                    mov+=2;
                }
                i--;
            }
            inicio++;
        }
    }
    //pai esta entre o meio do vetor; calculamos as posições dos filhos(fe e fd), pegamos o maior filho e trocamos com o pai, assim sucessivamente ate que o o maior numero esteja na posição 0
    //dai colocamos o maior numero no final(permutação), depois diminuimos o espaço de busca em -1;
    public void heapSort(){
        int maior, pos_pai;
        Registro pai = new Registro(), fd = new Registro(), fe = new Registro(), temp = new Registro(), regaux = new Registro();
        int tl2= filesize();
        while(tl2>1){
            pos_pai= tl2/2-1;
            while(pos_pai>=0){
                seekArq(2*pos_pai+1);
                fe.leDoArq(arquivo);
                if(!eof())
                    fd.leDoArq(arquivo);
                comp++;
                if(2*pos_pai+2<tl2 && fd.getNumero()> fe.getNumero())
                    maior = 2*pos_pai+2;
                else
                    maior = 2*pos_pai+1;

                seekArq(pos_pai);
                pai.leDoArq(arquivo);
                seekArq(maior);
                temp.leDoArq(arquivo);
                comp++;
                if (temp.getNumero()>pai.getNumero()){
                    seekArq(pos_pai);
                    temp.gravaNoArq(arquivo);
                    seekArq(maior);
                    pai.gravaNoArq(arquivo);
                }
                pos_pai--;
            }
            seekArq(tl2);
            regaux.leDoArq(arquivo);
            seekArq(0);
            pai.leDoArq(arquivo);
            seekArq(tl2);
            pai.gravaNoArq(arquivo);
            seekArq(0);
            regaux.gravaNoArq(arquivo);
            tl2--;
            mov+=2;
        }
    }

    public void shellSort(){
        Registro regi = new Registro(), regj = new Registro();
        int pos, tl = filesize(), dis =1, temp;
        while(dis<tl)
            dis = dis*3+1;
        dis = dis/3;
        while(dis>0){
            for(int i=dis;i<tl;i++){
                 pos = i;
                 seekArq(pos);
                 regi.leDoArq(arquivo);
                 temp = pos-dis;
                 seekArq(pos-dis);
                 regj.leDoArq(arquivo);
                 comp++;
                 while(pos>=dis && regi.getNumero()< regj.getNumero()){
                     seekArq(pos);
                     regj.gravaNoArq(arquivo);
                     mov++;
                     pos = pos-dis;
                     if(pos>=dis){
                         seekArq(pos-dis);
                         regj.leDoArq(arquivo);
                     }
                    comp++;
                 }
                 seekArq(pos);
                 regi.gravaNoArq(arquivo);
                 mov++;

            }
            dis = dis/2;
        }
    }
    public void GnomeSort(){
        int tl = filesize();
        Registro regi = new Registro(), rej = new Registro();
        int i =0;
        while (i < tl) {
            if(i==0){
                seekArq(i);
                rej.leDoArq(arquivo);
                regi.leDoArq(arquivo);
                i++;
            }
            comp++;
            if(regi.getNumero()>=rej.getNumero()){
                i++;
                seekArq(i-1);
                rej.leDoArq(arquivo);
                regi.leDoArq(arquivo);
            }else{
                seekArq(i-1);
                regi.gravaNoArq(arquivo);
                rej.gravaNoArq(arquivo);
                i--;
                mov+=2;
                if(i>0){
                    seekArq(i-1);
                    rej.leDoArq(arquivo);
                    regi.leDoArq(arquivo);
                }
            }
        }

    }
    public void CombSort(){
        int tl = filesize();
        Registro regi = new Registro(), regj = new Registro();
        int range = (int)(tl/1.3);
        boolean flag = true;
        int i =0;
        int j=0;
        while(range>0 || flag){
            flag =false;
            i = 0;
            j = range;
            while(j<tl){
                seekArq(i);
                regi.leDoArq(arquivo);
                seekArq(j);
                regj.leDoArq(arquivo);
                comp++;
                if(regi.getNumero()>regj.getNumero()) {
                    seekArq(j);
                    regi.gravaNoArq(arquivo);
                    seekArq(i);
                    regj.gravaNoArq(arquivo);
                    flag = true;
                    mov+=2;
                }
                i++;
                j++;
            }

            range = (int) (range/1.3);
        }
    }

    public int BuscaMaior(){
        seekArq(0);
        Registro aux = new Registro();
        int maior = -1;
        while(!eof()){
            aux.leDoArq(arquivo);
            comp++;
            if(aux.getNumero()>maior)
                maior = aux.getNumero();
        }
        return maior;
    }

    public void BucketSort() throws FileNotFoundException {
        Arquivo[] buckets = new Arquivo[10];

        int maior = BuscaMaior();
        Registro reg = new Registro();
        int pos;
        seekArq(0);
        for (int i = 0; i < 10; i++) {
            buckets[i] = new Arquivo(""+i); // crio o arquivo com base no numero
        }
        while(!eof()){
            reg.leDoArq(arquivo);
            pos = (int) (reg.getNumero()/(maior+1) * 10);
            buckets[pos].inserirnoFinal(reg.getNumero());
        }

        for (int i = 0; i < 10; i++) {
            buckets[i].initComp();
            buckets[i].initMov();
            buckets[i].insertionSort();
            mov+=buckets[i].getMov();
            comp+=buckets[i].getComp();

        }
        truncate(0);
        for (int i = 0; i < 10; i++) {
            buckets[i].seekArq(0);
            while(!buckets[i].eof()){
                reg.leDoArq(buckets[i].arquivo);
                reg.gravaNoArq(arquivo);
                mov++;
            }
            buckets[i].ExcluirArq();
        }
    }

    public void RadixSort() throws FileNotFoundException {
        int maior = BuscaMaior();
        int dig = 1;
        while(maior/dig>0){
            Couting_rd(dig);
            dig*=10;
        }
    }

    public void Couting_rd(int dig) throws FileNotFoundException {

        Registro reg = new Registro();
        int maior = BuscaMaior();
        int pos;
        int[] contagem = new int[10];
        seekArq(0);
        while(!eof()){
            reg.leDoArq(arquivo);
            pos = (reg.getNumero()/dig)%10;
            contagem[pos]++;
        }

        for (int i = 1; i < contagem.length; i++) {
            contagem[i] = contagem[i] + contagem[i-1];
        }

        Arquivo novo = new Arquivo("ArqRandNew");
        novo.geraArquivoOrdenado(filesize());
        int i = filesize()-1;
        seekArq(i);
        while(i>=0){
            reg.leDoArq(arquivo);
            novo.seekArq(contagem[(reg.getNumero()/dig)%10]-1);
            reg.gravaNoArq(novo.arquivo);
            contagem[(reg.getNumero()/dig)%10]--;
            i--;
            seekArq(i);
            mov++;
        }
        ExcluirArq();
        try {
            novo.arquivo.close();
            arquivo.close();

            File file = new File(nomearquivo);
            file.delete();
            file = new File(novo.nomearquivo);
            file.renameTo(new File(nomearquivo));
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e) {

        }
    }

    public void InsertionSort_t(int ini, int fim){
        int pos =ini+1;
        Registro regi = new Registro(), regj = new Registro();
        int i;
        while(pos<fim){
            i= pos;
            seekArq(i-1);
            regj.leDoArq(arquivo);
            regi.leDoArq(arquivo);
            comp++;
            while(i>ini && regj.getNumero()>regi.getNumero()){
                seekArq(i);
                regj.gravaNoArq(arquivo);
                mov++;
                i--;
                if(i>ini){
                    seekArq(i-1);
                    regj.leDoArq(arquivo);
                }
                comp++;
            }
            seekArq(i);
            regi.gravaNoArq(arquivo);
            pos++;
            mov++;
        }
    }
    public void FusaoTIM(int ini1, int fim1, int ini2, int fim2, Arquivo aux){
        Registro registro1 = new Registro(), registro2 = new Registro();
        aux.seekArq(0);
        int i = ini1;
        int j = ini2;

        while(i<=fim1 && j<=fim2){
            seekArq(i);
            registro1.leDoArq(arquivo);
            seekArq(j);
            registro2.leDoArq(arquivo);
            comp++;
            if(registro1.getNumero()<registro2.getNumero()){
                registro1.gravaNoArq(aux.arquivo);
                i++;
                mov++;
            }else{
                registro2.gravaNoArq(aux.arquivo);
                j++;
                mov++;
            }
        }
        seekArq(i);
        while(i<=fim1){
            registro1.leDoArq(arquivo);
            registro1.gravaNoArq(aux.arquivo);
            i++;
            mov++;
        }
        seekArq(j);
        while(j<=fim2){
            registro2.leDoArq(arquivo);
            registro2.gravaNoArq(aux.arquivo);
            j++;
            mov++;
        }
        int k = aux.filesize();
        aux.seekArq(0);
        seekArq(ini1);
        for (int t = 0; t < k; t++) {
            registro1.leDoArq(aux.arquivo);
            registro1.gravaNoArq(arquivo);
            mov++;

        }
        aux.truncate(0);
    }

    public void Merge(int esq, int dir, Arquivo aux){
        if(esq<dir){
            int meio = (esq+dir)/2;
            Merge(esq,meio,aux);
            Merge(meio+1, dir, aux);
            FusaoTIM(esq, meio, meio+1,dir, aux);
        }
    }

    public void MergeSort2() throws FileNotFoundException {
        Arquivo aux = new Arquivo("Merge2Aux");
        Merge(0, filesize()-1,aux);
        aux.ExcluirArq();
    }

    public void Merge1() throws FileNotFoundException {
        int seq = 1;
        int tl = filesize();
        Arquivo arq1 = new Arquivo("part1"), arq2 = new Arquivo("part2");
        while(seq<tl){
            particao(arq1, arq2);
            fusao(arq1,arq2,seq);
            seq= seq*2;
        }
        arq1.ExcluirArq();
        arq2.ExcluirArq();

    }
    public void fusao(Arquivo arq1, Arquivo arq2, int seq){
        int i=0, j=0, k=0, auxseq = seq;
        int tl = filesize();
        Registro regi = new Registro(), regj = new Registro();
        seekArq(0);
        while(k<tl){
            while(i<seq && j<seq){
                arq1.seekArq(i);
                regi.leDoArq(arq1.arquivo);
                arq2.seekArq(j);
                regj.leDoArq(arq2.arquivo);
                comp++;
                if(regi.getNumero()<regj.getNumero()){
                    regi.gravaNoArq(arquivo);
                    k++;
                    i++;
                }else{
                    regj.gravaNoArq(arquivo);
                    k++;
                    j++;

                }
                mov++;
            }
            while(i<seq){
                arq1.seekArq(i);
                regi.leDoArq(arq1.arquivo);
                regi.gravaNoArq(arquivo);
                k++;
                i++;
                mov++;
            }
            while(j<seq){
                arq2.seekArq(j);
                regj.leDoArq(arq2.arquivo);
                regj.gravaNoArq(arquivo);
                k++;
                j++;
                mov++;
            }
            seq= seq + auxseq;
        }
    }
    public void particao(Arquivo arquivo1, Arquivo arquivo2){
        int meio = filesize()/2;
        Registro regi = new Registro();
        arquivo1.seekArq(0);
        seekArq(0);
        for (int i = 0; i < meio; i++) {
            regi.leDoArq(arquivo);
            regi.gravaNoArq(arquivo1.arquivo);
            mov++;
        }
        arquivo2.seekArq(0);
        for (int i = meio; i < filesize(); i++) {
            regi.leDoArq(arquivo);
            regi.gravaNoArq(arquivo2.arquivo);
            mov++;
        }
    }
    public void TimSort() throws FileNotFoundException {
        int range = 32;
        int size = filesize();
        int inicio=0, fim;
        if(range>size)
            fim=size;
        else
            fim = 32;

        while(inicio<fim){
            InsertionSort_t(inicio,fim);
            if(fim+range>=size)
                fim=size;
            else
                fim+=range;

            inicio+=range;
        }
        Arquivo aux = new Arquivo("TImAux");
        int ini2, fim2;

        while(range<size){
            inicio=0;
            while(inicio<size){
                fim = inicio+range-1;
                if(fim<size){
                    ini2= fim+1;
                    fim2=ini2+range-1;
                    if(fim2>=size){
                        fim2=size-1;
                    }
                    FusaoTIM(inicio,fim,ini2,fim2, aux);
                }

                inicio+=range*2;
            }
            range*=2;
        }
        aux.ExcluirArq();
    }

    public void CoutingSort() throws IOException {
        Registro reg = new Registro();
        int maior = BuscaMaior();
        int[] contagem = new int[maior+1];
        seekArq(0);
        while(!eof()){
            reg.leDoArq(arquivo);
            contagem[reg.getNumero()]++;
        }

        for (int i = 1; i < contagem.length; i++) {
            contagem[i] = contagem[i] + contagem[i-1];
        }

        Arquivo novo = new Arquivo("ArqRandNew");
        novo.geraArquivoOrdenado(filesize());
        seekArq(0);
        while(!eof()){
            reg.leDoArq(arquivo);
            novo.seekArq(contagem[reg.getNumero()]-1);
            reg.gravaNoArq(novo.arquivo);
            contagem[reg.getNumero()]--;
            mov++;
        }
        ExcluirArq();
        try {
            novo.arquivo.close();
            arquivo.close();

            File file = new File(nomearquivo);
            file.delete();
            file = new File(novo.nomearquivo);
            file.renameTo(new File(nomearquivo));
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e) {

        }
    }
    public void selectionsort(){
        int posmenor;
        int tl = filesize();
        Registro pos = new Registro(), aux = new Registro(), aux2 = new Registro();
        for (int i = 0; i < tl-1; i++) {
            seekArq(i);
            pos.leDoArq(arquivo);
            posmenor=i;
            for(int j = i+1; j<tl;j++){
                aux.leDoArq(arquivo);
                comp++;
                if(aux.getNumero()<pos.getNumero()){
                    posmenor = j;
                    pos = new Registro(aux.getNumero());
                }
            }

            seekArq(i);
            aux2.leDoArq(arquivo);
            seekArq(i);
            pos.gravaNoArq(arquivo);
            seekArq(posmenor);
            aux2.gravaNoArq(arquivo);
            mov+=2;
        }
    }
    public void QuickP(int inicio, int fim){
        int i = inicio, j = fim;
        Registro regi = new Registro(), regj = new Registro(), pivo = new Registro();
        seekArq((inicio+fim)/2);
        pivo.leDoArq(arquivo);
        while(i<j){
            seekArq(i);
            regi.leDoArq(arquivo);
            comp++;
            while(regi.getNumero()<pivo.getNumero()){
                i++;
                seekArq(i);
                regi.leDoArq(arquivo);
                comp++;
            }

            seekArq(j);
            regj.leDoArq(arquivo);
            comp++;
            while(regj.getNumero()>pivo.getNumero()){
                j--;
                seekArq(j);
                regj.leDoArq(arquivo);
                comp++;
            }

            if(i<=j){
                if(i!=j){
                    seekArq(i);
                    regj.gravaNoArq(arquivo);
                    seekArq(j);
                    regi.gravaNoArq(arquivo);
                    mov+=2;
                }
                i++;
                j--;
            }
        }

        if(inicio<j)
            QuickP(inicio,j);
        if(i<fim)
            QuickP(i,fim);

    }

    private void QuickSP(int inicio, int fim){
        int i = inicio, j = fim;
        Boolean flag = true;
        Registro regi = new Registro(), regj = new Registro();
        while(i<j){
            seekArq(i);
            regi.leDoArq(arquivo);
            seekArq(j);
            regj.leDoArq(arquivo);
            comp++; // essa comparação é para somar nos dois whiles, pois vai cair ou no if ou no else e vai comparar
            if(flag)
                while(i<j && regi.getNumero()<=regj.getNumero()){
                    i++;
                    seekArq(i);
                    regi.leDoArq(arquivo);
                    comp++;
                }
            else
                while(i<j && regj.getNumero()>= regi.getNumero()){
                    j--;
                    seekArq(j);
                    regj.leDoArq(arquivo);
                    comp++;
                }
            if(i<j){
                seekArq(j);
                regi.gravaNoArq(arquivo);
                seekArq(i);
                regj.gravaNoArq(arquivo);
                mov+=2;
            }
            flag = !flag;
        }

        if(inicio<i-1)
            QuickSP(inicio, i-1);
        if(j+1<fim)
            QuickSP(j+1, fim);
    }

    public void QuickSemP(){
        QuickSP(0, filesize()-1);
    }
    public void QuickPivo(){
        QuickP(0,filesize()-1);
    }

    public void inserirnoFinal(int info){
        Registro aux = new Registro(info);
        seekArq(filesize());
        aux.gravaNoArq(arquivo);
    }

    public void geraArquivoOrdenado(int temp){

        Registro aux ;
        for (int i = 0; i < temp; i++) {
            aux = new Registro(i);
            aux.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoReverso(int temp){
        Registro aux ;
        for (int i = temp; i > 0; i--) {
            aux = new Registro(i);
            aux.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoRandomico(int temp) {
        Registro aux;
        Random random = new Random();
        seekArq(0);
        for (int i = 0; i < temp; i++) {
            aux = new Registro(random.nextInt(1024));
            aux.gravaNoArq(arquivo);
        }
    }
    public void geraArquivoTeste() {
        Registro aux;
        List<Integer> numerosal = List.of(4,2,1,11,6,12,9,7);
        Random random = new Random();
        seekArq(0);

        for (Integer temp : numerosal) {
            aux = new Registro(temp);
            aux.gravaNoArq(arquivo);
        }
    }

    public void Exibe() {
        Registro aux = new Registro();
        seekArq(0);
        for (int i = 0; !eof(); i++) {
            aux.leDoArq(arquivo);
            System.out.println(aux.getNumero());
        }
    }

    public void ExcluirArq(){
        Path arquivoParaExcluir = Paths.get(""+nomearquivo);
        try {
            arquivo.close();
            Files.delete(arquivoParaExcluir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
