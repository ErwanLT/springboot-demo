package fr.eletutour.service;


import fr.eletutour.model.Block;
import fr.eletutour.model.Transaction;
import fr.eletutour.util.HashUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BlockchainService {

    // chaîne en mémoire (thread-safe)
    private final List<Block> chain = new CopyOnWriteArrayList<>();
    // transactions en attente
    private final List<Transaction> currentTransactions = new ArrayList<>();

    // difficulté (nombre de zéros devant le hash)
    private final int difficulty = 6;

    public BlockchainService() {
        chain.add(Block.genesis());
    }

    public List<Block> getChain() {
        return chain;
    }

    public List<Transaction> getPendingTransactions() {
        return currentTransactions;
    }

    public synchronized int addTransaction(String sender, String recipient, double amount) {
        Transaction tx = new Transaction(sender, recipient, amount);
        currentTransactions.add(tx);
        // retourne l'index du bloc qui contiendra cette transaction
        return lastBlock().getIndex() + 1;
    }

    public synchronized Block mine(String minerAddress) {
        // récompense de minage simple
        Transaction reward = new Transaction("network", minerAddress, 1.0);
        currentTransactions.add(reward);

        Block last = lastBlock();
        int index = last.getIndex() + 1;
        long timestamp = Instant.now().toEpochMilli();

        // trouver nonce qui produit un hash valide
        long nonce = proofOfWork(index, timestamp, currentTransactions, last.getHash());
        String hash = calculateHash(index, timestamp, currentTransactions, nonce, last.getHash());

        // créer le bloc final
        Block newBlock = new Block(index, timestamp, List.copyOf(currentTransactions), nonce, last.getHash(), hash);
        chain.add(newBlock);

        // clear pending transactions
        currentTransactions.clear();

        return newBlock;
    }

    private Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

    private String calculateHash(int index, long timestamp, List<Transaction> transactions, long nonce, String previousHash) {
        String data = index + Long.toString(timestamp) + transactions.toString() + nonce + previousHash;
        return HashUtil.sha256(data);
    }

    private long proofOfWork(int index, long timestamp, List<Transaction> transactions, String previousHash) {
        long nonce = 0;
        String targetPrefix = "0".repeat(difficulty);
        while (true) {
            String h = calculateHash(index, timestamp, transactions, nonce, previousHash);
            if (h.startsWith(targetPrefix)) {
                return nonce;
            }
            nonce++;
        }
    }

    public boolean validateChain() {
        for (int i = 1; i < chain.size(); i++) {
            Block previous = chain.get(i - 1);
            Block current = chain.get(i);

            // vérif hash précédent
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }

            // vérifier hash courant (recalcul)
            String recalculated = calculateHash(current.getIndex(), current.getTimestamp(), current.getTransactions(), current.getNonce(), current.getPreviousHash());
            if (!recalculated.equals(current.getHash())) {
                return false;
            }

            // verificaion Proof-of-Work
            if (!current.getHash().startsWith("0".repeat(difficulty))) {
                return false;
            }
        }
        return true;
    }
}
