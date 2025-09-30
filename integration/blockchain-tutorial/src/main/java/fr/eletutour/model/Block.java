package fr.eletutour.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Block {
    private final int index;
    private final long timestamp;
    private final List<Transaction> transactions;
    private final long nonce;
    private final String previousHash;
    private final String hash;

    public Block(int index, long timestamp, List<Transaction> transactions, long nonce, String previousHash, String hash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.hash = hash;
    }

    public int getIndex() { return index; }
    public long getTimestamp() { return timestamp; }
    public List<Transaction> getTransactions() { return transactions; }
    public long getNonce() { return nonce; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }

    public static Block genesis() {
        return new Block(0, Instant.now().toEpochMilli(), List.of(), 0L, "0", "0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block block = (Block) o;
        return index == block.index && timestamp == block.timestamp && nonce == block.nonce && Objects.equals(transactions, block.transactions) && Objects.equals(previousHash, block.previousHash) && Objects.equals(hash, block.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, timestamp, transactions, nonce, previousHash, hash);
    }
}
