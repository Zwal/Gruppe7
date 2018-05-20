package de.repair.repairondemand.SQLlite;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.repair.repairondemand.SQLlite.Modells.Adresse;

public class AdresseArray {
    List<Adresse> adresseList = new List<Adresse>() {
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
        public Iterator<Adresse> iterator() {
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
        public boolean add(Adresse adresse) {
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
        public boolean addAll(@NonNull Collection<? extends Adresse> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends Adresse> c) {
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
        public Adresse get(int index) {
            return null;
        }

        @Override
        public Adresse set(int index, Adresse element) {
            return null;
        }

        @Override
        public void add(int index, Adresse element) {

        }

        @Override
        public Adresse remove(int index) {
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
        public ListIterator<Adresse> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Adresse> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<Adresse> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    public AdresseArray(){

    }

    public List<Adresse> getAdresseList() {
        return adresseList;
    }
}
