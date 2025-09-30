package fr.eletutour.controller;

import fr.eletutour.model.Block;
import fr.eletutour.service.BlockchainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BlockchainController {

    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping("/chain")
    public ResponseEntity<List<Block>> chain() {
        return ResponseEntity.ok(blockchainService.getChain());
    }

    @PostMapping("/transactions/new")
    public ResponseEntity<Map<String, Object>> newTransaction(@RequestBody Map<String, Object> body) {
        String sender = (String) body.get("sender");
        String recipient = (String) body.get("recipient");
        double amount = Double.parseDouble(body.get("amount").toString());
        int index = blockchainService.addTransaction(sender, recipient, amount);
        return ResponseEntity.ok(Map.of("message", "Transaction will be added to Block " + index));
    }

    @PostMapping("/mine")
    public ResponseEntity<Map<String, Object>> mine(@RequestBody Map<String, Object> body) {
        String minerAddress = (String) body.get("miner");
        Block block = blockchainService.mine(minerAddress);
        return ResponseEntity.ok(Map.of(
                "message", "New Block Forged",
                "index", block.getIndex(),
                "hash", block.getHash(),
                "nonce", block.getNonce()
        ));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate() {
        boolean valid = blockchainService.validateChain();
        return ResponseEntity.ok(Map.of("valid", valid));
    }

    @GetMapping("/pending")
    public ResponseEntity<Object> pending() {
        return ResponseEntity.ok(blockchainService.getPendingTransactions());
    }
}
