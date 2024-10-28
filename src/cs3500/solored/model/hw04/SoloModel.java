package cs3500.solored.model.hw04;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.solored.model.hw02.Color;
import cs3500.solored.model.hw02.PlayCard;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * The abstract class for a model of a solo game of RedSeven.
 *
 * <p>The Model's job is to handle data given to it,
 * and prevent the possibility of Illegal Game States
 * or Arguments.
 */
public abstract class SoloModel implements RedGameModel<PlayCard> {
  // NOTE: all lists are 0 indexed.
  protected List<PlayCard> deck;
  protected ArrayList<ArrayList<PlayCard>> palettes;
  protected PlayCard canvas;
  protected List<PlayCard> hand;
  protected int maxHandSize;
  protected int numPalettes;
  protected boolean gameStartedHuh;
  protected boolean changedCanvasThisTurnHuh;
  protected Random rand;
  protected int curWinningPaletteIndex;
  protected int previousWinningPaletteIndex;


  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (!gameStartedHuh || isGameOver()) {
      throw new IllegalStateException("Cannot play when game is not active.");
    } else if (paletteIdx < 0 || paletteIdx >= numPalettes) {
      throw new IllegalArgumentException("Palette index out of range");
    } else if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Card index out of range");
    } else if (paletteIdx == winningPaletteIndex()) {
      throw new IllegalStateException("Cannot play to winning palette");
    } else {
      previousWinningPaletteIndex = winningPaletteIndex();
      PlayCard toPalette = this.hand.remove(cardIdxInHand);
      palettes.get(paletteIdx).add(toPalette);
      curWinningPaletteIndex = winningPaletteIndex();
    }
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    canvasExceptions(cardIdxInHand);
    canvas = hand.remove(cardIdxInHand);
    changedCanvasThisTurnHuh = true;

  }

  /**
   * Throws appropriate exceptions for invalid input or game state to canvas.
   * @param cardIdxInHand card to check
   */
  protected void canvasExceptions(int cardIdxInHand) {
    if (!gameStartedHuh || isGameOver()) {
      throw new IllegalStateException("Cannot play when game is not active");
    } else if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Card index out of range");
    } else if (changedCanvasThisTurnHuh) {
      throw new IllegalStateException("Cannot change the canvas more than once per turn");
    } else if (hand.size() == 1) {
      throw new IllegalStateException("Cannot change canvas with only one card in hand");
    }
  }

  // Helper method to throw an exception for trying to play before the game has started
  private void gameNotStartedException() {
    if (!gameStartedHuh) {
      throw new IllegalStateException("Game has not started");
    }
  }

  @Override
  public int numOfCardsInDeck() {
    gameNotStartedException();
    return deck.size();
  }

  @Override
  public int numPalettes() {
    gameNotStartedException();
    return numPalettes;
  }


  @Override
  public void startGame(List<PlayCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameStartedHuh) {
      throw new IllegalStateException("Cannot start game when game is already started or over");
    } else if (numPalettes < 2 || handSize <= 0) {
      throw new IllegalArgumentException("Illegal palette or hand size");
    } else if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    } else if (!playableDeckSize(deck, numPalettes, handSize)) {
      throw new IllegalArgumentException("Deck is not big enough");
    } else if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck has null cards");
    } else if (!uniqueDeck(deck)) {
      throw new IllegalArgumentException("Deck has duplicate cards");
    }

    gameStartedHuh = true;

    List<PlayCard> gameDeck = copyLOPlayCard(deck);
    if (shuffle) {
      this.deck = shuffleDeck(gameDeck);
    } else {
      this.deck = gameDeck;
    }
    maxHandSize = handSize;
    this.numPalettes = numPalettes;
    palettes = new ArrayList<>();
    for (int palNum = 0; palNum < numPalettes; palNum++) {
      palettes.add(new ArrayList<>());
      PlayCard toPal = this.deck.remove(0);
      palettes.get(palNum).add(toPal);
    }
    hand = new ArrayList<>();
    drawForHand();
    curWinningPaletteIndex = winningPaletteIndex();

  }

  // Returns new list with a deep copy of all the PlayCards in the given list
  private List<PlayCard> copyLOPlayCard(List<PlayCard> list) {
    List<PlayCard> gameList = new ArrayList<>();
    for (PlayCard playCard : list) {
      PlayCard tempCard = playCard.copy();
      gameList.add(tempCard);
    }
    return gameList;
  }

  // Shuffles the given deck with the random object field
  // Modifying the given deck is ok, because it is a copy
  // of the deck that was given to us by the client,
  // and it becomes obsolete after shuffling.
  private List<PlayCard> shuffleDeck(List<PlayCard> deck) {
    List<PlayCard> shuffled = new ArrayList<>();
    int deckSize = deck.size();
    for (int i = 0; i < deckSize; i++) {
      int randIndex = rand.nextInt(deck.size());
      PlayCard shuffledCard = deck.remove(randIndex);
      shuffled.add(shuffledCard);
    }
    return shuffled;
  }

  // Helper method to determine if a deck has illegal cards for play
  private boolean uniqueDeck(List<PlayCard> deck) {
    for (int i = 0; i < deck.size(); i++) {
      List<PlayCard> subDeck = deck.subList(i + 1, deck.size());
      if (subDeck.contains(deck.get(i))) {
        return false;
      }
    }
    return true;
  }

  // Helper method to determine if the given deck can accommodate the hand/palette sizes
  private boolean playableDeckSize(List<PlayCard> deck, int numPalettes, int handSize) {
    return deck.size() - numPalettes - handSize >= 0;
  }


  @Override
  public int winningPaletteIndex() {
    gameNotStartedException();
    if (canvas.getColor().equals(Color.R)) {
      return redCanvasWinningPalette(palettes);
    } else if (canvas.getColor().equals(Color.O)) {
      return orangeCanvasWinningPalette();
    } else if (canvas.getColor().equals(Color.B)) {
      return blueCanvasWinningPalette();
    } else if (canvas.getColor().equals(Color.I)) {
      return indigoCanvasWinningPalette();
    } else {
      return violetCanvasWinningPalette();
    }
  }

  // The palette with the most cards below the value of 4 wins.
  private int violetCanvasWinningPalette() {
    ArrayList<PlayCard> curWinningCards = new ArrayList<>();
    int curWinningPal = -1;
    ArrayList<ArrayList<PlayCard>> tiedCards = new ArrayList<>();
    ArrayList<Integer> tiedPals = new ArrayList<>();

    for (int pal = 0; pal < palettes.size(); pal++) {
      ArrayList<PlayCard> curCards = new ArrayList<>();
      for (int card = 0; card < palettes.get(pal).size(); card++) {
        if (palettes.get(pal).get(card).getValue() < 4) {
          curCards.add(palettes.get(pal).get(card));
        }
      }
      if (curCards.size() > curWinningCards.size()) {
        curWinningCards = curCards;
        curWinningPal = pal;
        tiedCards = new ArrayList<>();
        tiedPals = new ArrayList<>();
      } else if (curCards.size() == curWinningCards.size()) {
        tiedCards.add(curCards);
        tiedPals.add(pal);
        if (!tiedPals.contains(curWinningPal)) {
          tiedCards.add(curWinningCards);
          tiedPals.add(curWinningPal);
        }
      }
    }
    if (!tiedPals.isEmpty()) {
      return breakTie(tiedPals, tiedCards);
    }
    return curWinningPal;
  }

  // The palette with the cards that form the longest run wins.
  private int indigoCanvasWinningPalette() {
    ArrayList<ArrayList<PlayCard>> longestRunsPalettes = new ArrayList<>();
    for (ArrayList<PlayCard> pal : palettes) {
      longestRunsPalettes.add(findLongestRun(pal));
    }

    ArrayList<PlayCard> largestRun = new ArrayList<>();
    int largestRunInx = -1;
    ArrayList<ArrayList<PlayCard>> tiedRuns = new ArrayList<>();
    ArrayList<Integer> tiedIdxs = new ArrayList<>();
    for (int run = 0; run < longestRunsPalettes.size(); run++) {
      if (longestRunsPalettes.get(run).size() > largestRun.size()) {
        largestRun = longestRunsPalettes.get(run);
        largestRunInx = run;
        tiedRuns = new ArrayList<>();
        tiedIdxs = new ArrayList<>();
      } else if (longestRunsPalettes.get(run).size() == largestRun.size()) {
        tiedRuns.add((longestRunsPalettes.get(run)));
        tiedIdxs.add(run);
        if (!tiedIdxs.contains(largestRunInx)) {
          tiedRuns.add(largestRun);
          tiedIdxs.add(largestRunInx);
        }
      }
    }
    if (tiedRuns.isEmpty()) {
      return largestRunInx;
    } else {
      return breakTie(tiedIdxs, tiedRuns);
    }
  }

  // Helper method to return the longest run of cards in the given palette
  private ArrayList<PlayCard> findLongestRun(ArrayList<PlayCard> pal) {
    ArrayList<ArrayList<PlayCard>> allRuns = new ArrayList<>();
    // initializes the beginning of all possible runs
    // i.e, all cards have a run of at least 1
    for (int card = 0; card < pal.size(); card++) {
      allRuns.add(new ArrayList<>());
      allRuns.get(card).add(pal.get(card));
    }
    for (int card = 0; card < pal.size(); card++) {
      PlayCard currentComparativeCard = pal.get(card);
      for (int curRun = 0; curRun < allRuns.size(); curRun++) {
        PlayCard lastCardInCurrentRun = allRuns.get(curRun).get(allRuns.get(curRun).size() - 1);
        if (currentComparativeCard.getValue() == lastCardInCurrentRun.getValue() + 1) {
          allRuns.get(curRun).add(currentComparativeCard);
        } else if (currentComparativeCard.getValue() == lastCardInCurrentRun.getValue()
                && !currentComparativeCard.equals(lastCardInCurrentRun)) {
          if (currentComparativeCard.getColor().getWeight()
                  > lastCardInCurrentRun.getColor().getWeight()) {
            allRuns.get(curRun).set(allRuns.get(curRun).size() - 1, currentComparativeCard);
          }
        }
      }
    }
    ArrayList<PlayCard> largestRun = new ArrayList<>();
    int largestRunInx = -1;
    ArrayList<ArrayList<PlayCard>> tiedRuns = new ArrayList<>();
    ArrayList<Integer> tiedIdxs = new ArrayList<>();
    for (int run = 0; run < allRuns.size(); run++) {
      if (allRuns.get(run).size() > largestRun.size()) {
        largestRun = allRuns.get(run);
        largestRunInx = run;
        tiedRuns = new ArrayList<>();
        tiedIdxs = new ArrayList<>();
      } else if (allRuns.get(run).size() == largestRun.size()) {
        tiedRuns.add((allRuns.get(run)));
        tiedIdxs.add(run);
        if (!tiedIdxs.contains(largestRunInx)) {
          tiedRuns.add(largestRun);
          tiedIdxs.add(largestRunInx);
        }
      }
    }
    if (tiedRuns.isEmpty()) {
      return largestRun;
    } else {
      int idxOfHighest = breakTie(tiedIdxs, tiedRuns);
      return allRuns.get(idxOfHighest);
    }
  }

  // Highest amount of colors wins
  private int blueCanvasWinningPalette() {
    int curHighestColors = 0;
    int curWinningPalette = -1;
    List<Integer> tiePaletteIdx = new ArrayList<>();

    for (int palIdx = 0; palIdx < palettes.size(); palIdx++) {
      List<Color> curColors = new ArrayList<>();
      for (int cardIdx = 0; cardIdx < palettes.get(palIdx).size(); cardIdx++) {
        PlayCard curCard = palettes.get(palIdx).get(cardIdx);
        if (!curColors.contains(curCard.getColor())) {
          curColors.add(curCard.getColor());
        }
      }
      // if current list of different colors is higher than the winning,
      // replace the winning high score winning index
      if (curColors.size() > curHighestColors) {
        curHighestColors = curColors.size();
        curWinningPalette = palIdx;
        tiePaletteIdx = new ArrayList<>();

        // if current list of different colors is the same length as the winning
        // then add current palette index into a tying list, and the winning index
        // if it has not already been added
      } else if (curColors.size() == curHighestColors) {
        tiePaletteIdx.add(palIdx);
        if (!tiePaletteIdx.contains(curWinningPalette)) {
          tiePaletteIdx.add(curWinningPalette);
        }
      }
    }
    if (tiePaletteIdx.isEmpty()) {
      return curWinningPalette;
    }
    // if there are ties, we create a new list of the tying palettes to send
    // to break the tie, along with a list of their original palette indices
    else {
      ArrayList<ArrayList<PlayCard>> tieBreakPalettes = new ArrayList<>();
      for (Integer paletteIdx : tiePaletteIdx) {
        tieBreakPalettes.add(palettes.get(paletteIdx));
      }
      return breakTie(tiePaletteIdx, tieBreakPalettes);
    }
  }

  // Palette with most of a single number wins
  private int orangeCanvasWinningPalette() {
    ArrayList<PlayCard> curHighestSame = new ArrayList<>();
    int curWinningPalette = -1;
    List<Integer> tiePaletteIdx = new ArrayList<>();
    ArrayList<ArrayList<PlayCard>> tiePaletteValues = new ArrayList<>();

    for (int palIdx = 0; palIdx < palettes.size(); palIdx++) {
      for (int cardIdx = 0; cardIdx < palettes.get(palIdx).size(); cardIdx++) {
        PlayCard curCard = palettes.get(palIdx).get(cardIdx);
        int curVal = curCard.getValue();
        ArrayList<PlayCard> curSame = new ArrayList<>();
        curSame.add(curCard);
        for (int nextCard = 1; nextCard + cardIdx < palettes.get(palIdx).size(); nextCard++) {
          PlayCard otherCard = palettes.get(palIdx).get(cardIdx + nextCard);
          if (otherCard.getValue() == curVal) {
            curSame.add(otherCard);
          }
        }

        if (curSame.size() > curHighestSame.size()) {
          curHighestSame = curSame;
          curWinningPalette = palIdx;
          tiePaletteIdx = new ArrayList<>();
          tiePaletteValues = new ArrayList<>();
        } else if (curSame.size() == curHighestSame.size()) {
          tiePaletteIdx.add(palIdx);
          tiePaletteValues.add(curSame);
          if (!tiePaletteIdx.contains(curWinningPalette)) {
            tiePaletteIdx.add(curWinningPalette);
            tiePaletteValues.add(curHighestSame);
          }
        }
      }
    }
    if (!tiePaletteIdx.isEmpty()) {
      return breakTie(tiePaletteIdx, tiePaletteValues);
    }
    return curWinningPalette;
  }

  // Breaks a tie for the given palettes with the red rule
  // first list is the original indices of palettes listed in second list
  private int breakTie(List<Integer> originalPaletteIdxs,
                       ArrayList<ArrayList<PlayCard>> palettesThatTied) {
    int winningRedIdx = redCanvasWinningPalette(palettesThatTied);
    return originalPaletteIdxs.get(winningRedIdx);
  }

  // Palette with the highest card wins
  // Red cannot have ties because every card is unique
  private int redCanvasWinningPalette(ArrayList<ArrayList<PlayCard>> palettesForRed) {
    PlayCard curHighestCard = new PlayCard(Color.V, 1);
    int curWinningPalette = 0;
    for (int palIdx = 0; palIdx < palettesForRed.size(); palIdx++) {
      for (int cardIdx = 0; cardIdx < palettesForRed.get(palIdx).size(); cardIdx++) {
        PlayCard curCard = palettesForRed.get(palIdx).get(cardIdx);
        if (curCard.isHigherThan(curHighestCard)) {
          curHighestCard = curCard;
          curWinningPalette = palIdx;
        }
      }
    }
    return curWinningPalette;
  }

  @Override
  public boolean isGameOver() {
    gameNotStartedException();
    return (deck.isEmpty() && hand.isEmpty())
            || (previousWinningPaletteIndex == curWinningPaletteIndex);
  }

  @Override
  public boolean isGameWon() {
    if (!gameStartedHuh || !isGameOver()) {
      throw new IllegalStateException("Game cannot be won if game hasn't "
              + "been started or finished");
    }
    return (deck.isEmpty() && hand.isEmpty()
            || deck.isEmpty() && hand.size() == 1)
            && curWinningPaletteIndex != previousWinningPaletteIndex;
  }

  @Override
  public List<PlayCard> getHand() {
    gameNotStartedException();
    return copyLOPlayCard(hand);
  }

  @Override
  public List<PlayCard> getPalette(int paletteNum) {
    gameNotStartedException();
    if (paletteNum < 0 || paletteNum >= numPalettes) {
      throw new IllegalArgumentException("Invalid palette index");
    }
    return copyLOPlayCard(palettes.get(paletteNum));
  }

  @Override
  public PlayCard getCanvas() {
    gameNotStartedException();
    if (canvas.getValue() == -1) {
      return PlayCard.makeInitialCanvasCard();
    }
    return canvas.copy();
  }

  @Override
  public List<PlayCard> getAllCards() {

    List<PlayCard> allCards = new ArrayList<>();

    for (int color = 0; color < 5; color++) {
      Color curColor;
      if (color == 0) {
        curColor = Color.R;
      } else if (color == 1) {
        curColor = Color.O;
      } else if (color == 2) {
        curColor = Color.B;
      } else if (color == 3) {
        curColor = Color.I;
      } else {
        curColor = Color.V;
      }
      for (int value = 1; value < 8; value++) {
        PlayCard cardToAdd = new PlayCard(curColor, value);
        allCards.add(cardToAdd);
      }
    }
    return allCards;
  }
}
