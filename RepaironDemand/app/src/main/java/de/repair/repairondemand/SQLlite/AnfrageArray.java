package de.repair.repairondemand.SQLlite;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.repair.repairondemand.SQLlite.Modells.Anfrage;

public class AnfrageArray {
    private ArrayList<Anfrage> manfrageList = new ArrayList<Anfrage>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Anfrage> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(Anfrage anfrage) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Anfrage> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Anfrage> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Anfrage get(int index) {
            return null;
        }

        @Override
        public Anfrage set(int index, Anfrage element) {
            return null;
        }

        @Override
        public void add(int index, Anfrage element) {

        }

        @Override
        public Anfrage remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Anfrage> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Anfrage> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Anfrage> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
     public AnfrageArray(){

     }

    public ArrayList<Anfrage> getAnfrageList() {
        return manfrageList;
    }
}

