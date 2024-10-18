package br.com.fiap.javabet.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
