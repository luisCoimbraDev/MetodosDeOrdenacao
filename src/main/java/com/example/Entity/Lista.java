package com.example.Entity;

import java.util.ArrayList;
import java.util.List;

public class Lista {
    private noLista inicio;
    private int tl;

    public Lista(){
        inicio = null;
        tl = 0;
    }
    public noLista getInicio(){
        return inicio;
    }
    public int getTl(){
        return tl;
    }
    public void inserirnaLista(int elem){
        noLista nolista = new noLista(elem, null,null);
        if(inicio==null){
            inicio = nolista;
        }
        else{
            noLista aux = inicio;
            while(aux.getProximo()!=null)
                aux = aux.getProximo();
            aux.setProximo(nolista);
            nolista.setAntetior(aux);
        }
        tl++;
    }

    public void inserirnaLista(noLista no){
        if(inicio==null){
            inicio = no;
        }
        else{
            noLista aux = inicio;
            while(aux.getProximo()!=null)
                aux = aux.getProximo();
            aux.setProximo(no);
            no.setAntetior(aux);
        }
        tl++;
    }

    public void selectionSort(){
        noLista menor, i = inicio, pos;
        int aux;
        while(i!=null){
            menor = i;
            pos = i.getProximo();
            while(pos!=null){
                if(pos.getInfo()<menor.getInfo())
                    menor = pos;
                pos = pos.getProximo();
            }
            aux = i.getInfo();
            i.setInfo(menor.getInfo());
            menor.setInfo(aux);

            i=i.getProximo();
        }
    }

    public void insertionSort(){
        noLista pos, i;
        int aux;
        i = inicio.getProximo();
        while(i!=null){
            aux = i.getInfo();
            pos = i;
            while(pos!=inicio && aux<pos.getAnterior().getInfo()){
                pos.setInfo(pos.getAnterior().getInfo());
                pos = pos.getAnterior();
            }
            pos.setInfo(aux);
            i = i.getProximo();

        }
    }


    public noLista BinarySearch(int chave, int tl2){
        int fim = tl2-1 , ini=0, meio;
        noLista pos = inicio;
        int i=0;
        meio = fim/2;
        while(i<meio){
            pos = pos.getProximo();
            i++;
        }
        while(ini<fim){
            if(chave>pos.getInfo())
                ini = meio+1;
            else
                fim= meio-1;

            meio = (ini+fim)/2;

            if(i<meio)
                while(i<meio){
                    pos = pos.getProximo();
                    i++;
                }
            else
                while(i>meio){
                    pos = pos.getAnterior();
                    i--;
                }
        }

        if(chave>pos.getInfo())
            return pos.getProximo();
        return pos;
    }

    public void insertionBinary(){
        int i=1,aux;
        noLista pos = inicio.getProximo(), ppos, paux; // pos esta representando o i;
                                                        // o ppos esta representanto a melhor posição para inserir
        while(i<tl){
            aux = pos.getInfo();
            ppos = BinarySearch(aux, i); // daqui a gnt pode tirar que a posição é menor igual a i;
            for(paux=pos; paux!=ppos; paux=paux.getAnterior()){ // como eu sei que ppos(melhor posição) é menor que o pos(i), faço outro ponteiro começar de i e decrementa ate chegar na melhor posição para inserir
                paux.setInfo(paux.getAnterior().getInfo()); // aqui to "empurrando" todos para inserir na melhor posição
            }
            ppos.setInfo(aux);

            i++;
            pos = pos.getProximo();
        }

    }

    public void BubbleSort(){
        boolean flag = true;
        int aux;
        int i , tl2 =tl;
        noLista pos2;
        while(tl2>1 && flag){
            flag = false;
            pos2 = inicio;
            i=0;
            while(i<tl2-1){
                if(pos2.getInfo()>pos2.getProximo().getInfo()){
                    aux = pos2.getProximo().getInfo();
                    pos2.getProximo().setInfo(pos2.getInfo());
                    pos2.setInfo(aux);
                    flag = true;
                }
                pos2 = pos2.getProximo();
                i++;
            }
            tl2--;
        }
    }


    public void ShakeSort(){
        int ini = 0, fim = tl-1, i=0;
        int aux;
        boolean flag = true;
        noLista pos2= inicio;
        noLista pos1 = inicio;
        while(ini<fim && flag){
            flag = false;
            while(i<fim){
                if(pos2.getInfo()>pos2.getProximo().getInfo()){
                    aux = pos2.getProximo().getInfo();
                    pos2.getProximo().setInfo(pos2.getInfo());
                    pos2.setInfo(aux);
                    flag = true;
                }
                pos2 = pos2.getProximo();
                i++;
            }
            fim--;
            i=fim;
            pos1 = pos2.getAnterior();
            while(i>ini){
                if(pos1.getInfo()<pos1.getAnterior().getInfo()){
                    aux = pos1.getAnterior().getInfo();
                    pos1.getAnterior().setInfo(pos1.getInfo());
                    pos1.setInfo(aux);
                    flag = true;
                }
                pos1 = pos1.getAnterior();
                i--;
            }
            ini++;
            i=ini;
            pos2= pos1.getProximo();
        }
    }

    public noLista mudaPonteiroE(int pos){
        noLista point = inicio;
        for (int i = 0; i < pos; i++) {
            point = point.getProximo();
        }
        return point;
    }

    public void HeapSort(){
        noLista maior, fim, filhoE, filhoD, dad;
        int fd,pai, tl2 = tl, aux;
        fim = mudaPonteiroE(tl-1);
        while(tl2>1){
            pai = tl2/2-1;
            dad = mudaPonteiroE(pai);
            while(pai>=0){
                filhoE = mudaPonteiroE(2*pai+1);
                filhoD = filhoE.getProximo();
                fd = 2*pai+2;
                if(fd<tl2 && filhoD.getInfo()>filhoE.getInfo())
                    maior = filhoD;
                else
                    maior = filhoE;
                if(maior.getInfo()> dad.getInfo()){
                    aux = dad.getInfo();
                    dad.setInfo(maior.getInfo());
                    maior.setInfo(aux);
                }
                pai--;
                dad = dad.getAnterior();
            }
            aux = fim.getInfo();
            fim.setInfo(inicio.getInfo());
            inicio.setInfo(aux);
            fim = fim.getAnterior();
            tl2--;
        }
    }

    public noLista moverDist(int dis){
        noLista point = inicio;
        for (int i = 0; i < dis; i++) { // pode ter um problema na distancia, pois pode colocar uma casa mais pra frente
            point = point.getProximo();
        }
        return point;
    }


    public void ShellSort(){
        noLista  pos1, pos2;
        int aux;
        int pos;
        int dis = 1;
        while(dis<tl){
            dis = dis*3+1;
        }
        dis = dis/3;
        while(dis>0){
            for (int i = dis; i < tl; i++) {
                pos = i;
                pos1 = moverDist(pos);
                aux = pos1.getInfo();// para representar o pos original
                pos2 = moverDist(pos-dis);
                while(pos>=dis && aux<pos2.getInfo()){
                    pos1.setInfo(pos2.getInfo());
                    pos = pos-dis;
                    pos1 = moverDist(pos);
                    pos2 = moverDist(pos-dis);
                }
                pos1.setInfo(aux);
            }
            dis = dis/2;
        }
    }






    public void bucketSort(){
        int  max;

        noLista aux = inicio;
        max  = inicio.getInfo();
        while(aux!=null){
            if(aux.getInfo()>max)
                max = aux.getInfo();
            aux = aux.getProximo();
        }

        Lista[] list = new Lista[10];
        aux = inicio;
        int pos;

        while(aux!=null){
            pos = (int)(((double) aux.getInfo()/(max+1)) * 10);

            if(list[pos] == null)
                list[pos] = new Lista();
            list[pos].inserirnaLista(aux.getInfo());
            aux = aux.getProximo();
        }
        inicio = null;
        for (int i = 0; i < list.length; i++) {
            if(list[i]!=null){
                list[i].insertionSort();
                inserirnaLista(list[i].inicio);
            }
        }

    }

    public void coutingsort(){ // fazer a soma acumulativa depois (professor nao gostou)
        noLista aux = inicio;
        int maior = aux.getInfo();
        while(aux!=null){
            if (aux.getInfo()>maior)
                maior = aux.getInfo();
            aux = aux.getProximo();
        }

        int[] vet = new int[maior+1];
        for (int i = 0; i <=maior; i++) {
            vet[i] = 0;
        }
        aux = inicio;
        while (aux!=null) {
            vet[aux.getInfo()]++;
            aux = aux.getProximo();
        }
        aux = inicio;
        for (int i = 0; i <= maior ; i++) {
            while(vet[i]>0){
                aux.setInfo(i);
                vet[i]--;
                aux= aux.getProximo();
            }
        }
    }

    public void Couting_r(int digito){
        int[] vet = new int[10];
        Lista lista = new Lista();

        noLista aux = inicio;
        while(aux!=null){
            lista.inserirnaLista(0);
            aux = aux.getProximo();
        }


         aux = inicio;
        while(aux!=null){
            vet[(aux.getInfo()/digito)%10]++;
            aux = aux.getProximo();
        }

        for (int i = 1; i < 10; i++) {
            vet[i] = vet[i]+vet[i-1];
        }


//        int i;
//        aux = inicio;
        while (aux.getProximo()!=null) // tem que ser de tras para frente para manter a ordem de os menores estarem na menor posição do vetor(para que nas proximas vezes que ele passar, as menores posiçoes sejam atribuidas para os menores elementos)
            aux=aux.getProximo();
        int pos;
        while(aux!=null){
            pos = vet[(aux.getInfo()/digito)%10] -1;
            vet[(aux.getInfo()/digito)%10]--;
            insere_c(lista, pos, aux.getInfo());
            aux = aux.getAnterior();
        }

        this.inicio = lista.getInicio();



    }

    public void insere_c(Lista lista, int pos, int elem){
        noLista aux = lista.getInicio();
        while(aux!=null && pos>0){
            aux =aux.getProximo();
            pos--;
        }
        aux.setInfo(elem);
    }

    public void radixSort(){
        noLista aux= inicio.getProximo();
        int maior = inicio.getInfo();
        while (aux!=null){
            if(aux.getInfo()>maior)
                maior = aux.getInfo();
            aux = aux.getProximo();
        }

        int digito =1;
        while(maior/digito>0){
            Couting_r(digito);
            digito *=10;
        }

    }

    public void GnomeSort(){
        noLista aux = inicio;
        int temp;
        while(aux!=null){
            if(aux == inicio)
                aux = aux.getProximo();
            if(aux.getInfo()>=aux.getAnterior().getInfo())
                aux = aux.getProximo();
            else{
                temp = aux.getInfo();
                aux.setInfo(aux.getAnterior().getInfo());
                aux.getAnterior().setInfo(temp);
                aux = aux.getAnterior();
            }

        }

    }

    public void CombSort(){

        int range =(int)(tl/1.3);
        noLista auxi = inicio;
        noLista auxj;
        int temp;
        boolean flag = true;
        while(range>1 || flag){
            auxi =inicio;
            auxj =moverDist(range);
            flag = false;
            while(auxj!=null){
                if(auxj.getInfo()< auxi.getInfo()){
                    temp = auxj.getInfo();
                    auxj.setInfo(auxi.getInfo());
                    auxi.setInfo(temp);
                    flag = true;
                }
                auxj = auxj.getProximo();
                auxi = auxi.getProximo();
            }
            range/=1.3;
        }
    }

    void  insertionSort_t(noLista ini, noLista fim){
        int temp;
        noLista aux = ini.getProximo(), pos;
        while(aux!=fim.getProximo()){
            temp = aux.getInfo();
            pos=aux;
            while(pos!=ini && pos.getAnterior().getInfo()>temp){
                pos.setInfo(pos.getAnterior().getInfo());
                pos = pos.getAnterior();
            }
            pos.setInfo(temp);

            aux = aux.getProximo();
        }


    }

    public void particao(Lista p1, Lista p2){
        noLista aux = inicio;
        int meio = tl/2;
        for (int i = 0; i < meio; i++) {
            p1.inserirnaLista(aux.getInfo());
            aux = aux.getProximo();
        }
        for(int i=meio; i< tl;i++){
            p2.inserirnaLista(aux.getInfo());
            aux = aux.getProximo();
        }
    }

    public void merge1(){
        int seq = 1;
        Lista part1 = new Lista(), part2 = new Lista();

        while(seq<tl){
            particao(part1, part2);
            fusao1(part1,part2,seq);
            seq*=2;
        }
    }

    public void fusao1(Lista p1, Lista p2, int seq){
        int i=0, j=0, k=0, auxseq = seq;
        noLista aux = inicio;
        while(k<tl){
            while(i<seq && j<seq){
                if(p1.inicio.getInfo()<p2.inicio.getInfo()){
                    aux.setInfo(p1.inicio.getInfo());
                    aux = aux.getProximo();
                    k++;
                    i++;
                    p1.inicio = p1.inicio.getProximo();
                }else{
                    aux.setInfo(p2.inicio.getInfo());
                    aux = aux.getProximo();
                    k++;
                    j++;
                    p2.inicio = p2.inicio.getProximo();
                }
            }

            while(i<seq){
                aux.setInfo(p1.inicio.getInfo());
                aux = aux.getProximo();
                k++;
                i++;
                p1.inicio = p1.inicio.getProximo();
            }
            while (j<seq){
                aux.setInfo(p2.inicio.getInfo());
                aux = aux.getProximo();
                k++;
                j++;
                p2.inicio = p2.inicio.getProximo();
            }
            seq = seq + auxseq;
        }
    }

    public void TimSort(){
        int runs = 4;
        noLista regi, regj, aux;
        int temp;

        regi= inicio.getProximo();
        while(regi!=null){ // inserção direta ordenando as runs
            int i = 1;
            aux = regi.getAnterior();
            while(regi!=null && i<runs){
                regj = regi;
                temp= regj.getInfo();
                while(regj!=aux && temp<regj.getAnterior().getInfo()){
                    regj.setInfo(regj.getAnterior().getInfo());
                    regj = regj.getAnterior();
                }

                regj.setInfo(temp);
                regi = regi.getProximo();
                i++;
            }
            if(regi!=null)
                regi = regi.getProximo(); // isso para que quando ele acabe de ordenar por uma run, ele pegue o segundo elemento da run, para que siga o conceito do primeiro elemento já é ordenado
        }


        int ini1, fim1, ini2, fim2;
        Lista auxx = new Lista();
        while(runs<tl){//runs nao estão sendo separadas da maneira certa.
            ini1=0;
            while(ini1<tl){
                fim1 = ini1+runs-1;
                if(fim1<tl){
                    ini2= fim1+1;
                    fim2=ini2+runs-1;
                    if(fim2>=tl){
                        fim2=tl-1;
                    }
                    Fusao_t(ini1,fim1,ini2,fim2, auxx);
                }

                ini1+=runs*2;
            }

            runs*=2;
        }
    }

    public void QuickSemPivo(){
        QuickSP(0, tl-1);
    }

    private void QuickSP(int ini, int fim){
        noLista regi, regj;
        boolean flag = true;
        int i = ini, j = fim, aux;
        while (i<j){
            regi = moverDist(ini);
            regj = moverDist(j);
            if(flag)
                while(i<j && regi.getInfo()<=regj.getInfo()){
                    i++;
                    regi= regi.getProximo();
                }
            else
                while(i<j && regj.getInfo()>=regi.getInfo()){
                    j--;
                    regj.getAnterior();
                }

            if(i<j){
                aux = regi.getInfo();
                regi.setInfo(regj.getInfo());
                regj.setInfo(aux);
            }
            flag=!flag;

        }

        if(ini<i-1)
            QuickSP(ini,i-1);
        if(j+1<fim)
            QuickSP(j+1, fim);
    }
    private void QuickP(int ini, int fim){
        int i = ini, j = fim, aux;
        noLista regi, regj , pivo;
        pivo = moverDist((ini+fim)/2);
        regi = moverDist(i);
        regj = moverDist(j);
        while(i<j){

            while(regi.getInfo()<pivo.getInfo()){
                regi = regi.getProximo();
                i++;
            }


            while(regj.getInfo()> pivo.getInfo()){
                regj = regj.getAnterior();
                j--;
            }

            if(i<=j){
                aux = regi.getInfo();
                regi.setInfo(regj.getInfo());
                regj.setInfo(aux);
                regi = regi.getProximo();
                regj=regj.getAnterior();
                i++;
                j--;
            }
        }

        if(ini<j)
            QuickP(ini,j);
        if(i<fim)
            QuickP(i,fim);
    }

    public void QuickPivo(){
        QuickP(0, tl-1);
    }



    public void Merge2(int esq, int dir, Lista aux){
        if(esq<dir){
            int meio = (esq+dir)/2;
            Merge2(esq, meio, aux);
            Merge2(meio+1, dir, aux);
            Fusao_t(esq, meio, meio+1, dir, aux);
        }
    }

    public void MergeSort2(){
        Lista aux = new Lista();
        Merge2(0, tl-1, aux);
    }

    public void Fusao_t(int ini1, int fim1, int ini2, int fim2, Lista aux){
        noLista regi, regj;
        int k;
        int i = ini1, j=ini2;
        regi = moverDist(ini1);
        regj = moverDist(ini2);
        while(ini1<=fim1 && ini2<=fim2){
            if(regi.getInfo()<regj.getInfo()){
                aux.inserirnaLista(regi.getInfo());
                regi= regi.getProximo();
                ini1++;
            }else{
                aux.inserirnaLista(regj.getInfo());
                regj= regj.getProximo();
                ini2++;
            }
        }
        while(ini1<=fim1){
            aux.inserirnaLista(regi.getInfo());
            regi= regi.getProximo();
            ini1++;
        }
        while(ini2<=fim2){
            aux.inserirnaLista(regj.getInfo());
            regj= regj.getProximo();
            ini2++;
        }
        regi = moverDist(i);
        while(aux.inicio!=null){
            regi.setInfo(aux.inicio.getInfo());
            aux.inicio = aux.inicio.getProximo();
            regi = regi.getProximo();
        }

    }

    public void gerarNumerosseq(int num){
        for (int i = num; i >0; i--) {
            inserirnaLista(i);
        }

    }
    public void exibir(){
        noLista i = inicio;
        while(i!=null){
            System.out.println(i.getInfo());
            i=i.getProximo();
        }
    }
}
