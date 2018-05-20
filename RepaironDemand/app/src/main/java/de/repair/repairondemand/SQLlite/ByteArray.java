package de.repair.repairondemand.SQLlite;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ByteArray {
    List<byte[]> byteList = new List<byte[]>() {
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
        public Iterator<byte[]> iterator() {
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
        public boolean add(byte[] bytes) {
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
        public boolean addAll(@NonNull Collection<? extends byte[]> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends byte[]> c) {
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
        public byte[] get(int index) {
            return new byte[0];
        }

        @Override
        public byte[] set(int index, byte[] element) {
            return new byte[0];
        }

        @Override
        public void add(int index, byte[] element) {

        }

        @Override
        public byte[] remove(int index) {
            return new byte[0];
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
        public ListIterator<byte[]> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<byte[]> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<byte[]> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    public ByteArray(){

    }

    public List<byte[]> getByteList() {
        return byteList;
    }
}
