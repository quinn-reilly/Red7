Overview
This is a single-player implementation of the card game Red7. 
In this solitaire version, the player aims to manage their hand of cards 
and meet the current winning condition to stay in the game. The game
is easy to learn but requires strategic thinking as players can change the rules each turn.

Features
Single-player Mode: Play in a solitaire format without any opponents.
Rule Management: Game rules for each color condition are automatically enforced.
Dynamic Gameplay: Change the winning rule by playing cards from your hand to the canvas.

Turn: On your turn, you must ensure you are winning by the end:
Play a card to a palette to meet the current winning rule.
Change the winning rule by playing a card to the canvas.
Winning Condition: The rule defines what it means to win (e.g., highest card wins for "Red").
End of Game: The game ends when you play a card that does not meet the winning condition, or the deck is empty.

Changes from previous implementation:
- Abstract class SoloModel created, in order to minimize code duplication
  -> code refactored from SoloRedGameModel into SoloModel
- test classes TestController and TestModel refactored from inner packages into the
  cs3500.solored package
- Many tests originally in TestModel are now inside AbstractTestModel,
  to minimize code duplication as these tests work with either model.
