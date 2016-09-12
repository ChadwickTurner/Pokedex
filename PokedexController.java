package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PokedexController implements Initializable{

    @FXML
    private ListView<Pokemon> pokemonView;

    @FXML private Button sortButton;
    @FXML private ChoiceBox<String> typeBox;
    @FXML private ChoiceBox<String> type2Box;
    @FXML private CheckBox fullyEvolvedBox;
    @FXML private CheckBox megaEvoBox;
    @FXML private ChoiceBox<String> generationBox;

    @FXML private Label number;
    @FXML private Label name;
    @FXML private Label type1;
    @FXML private Label type2;
    @FXML private Label hp;
    @FXML private Label attack;
    @FXML private Label defense;
    @FXML private Label specialAttack;
    @FXML private Label specialDefense;
    @FXML private Label speed;
    @FXML private Label total;
    @FXML private Label average;

    @FXML private Hyperlink serebii;
    @FXML private Hyperlink smogon;




    private ObservableList<Pokemon> prevState;
    private ArrayList<String> searches; // {Type1, Type2, FullyEvolved, HasMegaEvo}
    private final ObservableList<Pokemon> orig = FXCollections.observableArrayList(generator(new File("src/gui/pokedex.csv")));

    public void initialize(URL location, ResourceBundle resources) {
        pokemonView.setItems(orig);
        pokemonView.getFocusModel().focus(0);
        pokemonView.getSelectionModel().select(0);

        pokemonView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pokemon>() {
            @Override
            public void changed(ObservableValue<? extends Pokemon> observable, Pokemon oldValue, Pokemon newValue) {
                if (newValue != null) {
                    name.setText(newValue.getName());
                    number.setText("" + newValue.getId());
                    type1.setText(Pokemon.typeToString(newValue.getTypes().get(0)));
                    if (newValue.getTypes().size() == 2) {
                        type2.setText(Pokemon.typeToString(newValue.getTypes().get(1)));
                    } else {
                        type2.setText("N/A");
                    }
                    hp.setText("" + newValue.getHP());
                    attack.setText("" + newValue.getATK());
                    defense.setText("" + newValue.getDEF());
                    specialAttack.setText("" + newValue.getSPA());
                    specialDefense.setText("" + newValue.getSPD());
                    speed.setText("" + newValue.getSPE());
                    total.setText("" + newValue.getTOT());
                    average.setText("" + newValue.getAVG());
                }
            }
        });
        searches = new ArrayList<>();
        searches.add("None");
        searches.add("None");
        searches.add("FALSE");
        searches.add("FALSE");
        searches.add("0");


        ArrayList<String> types = new ArrayList<>();
        types.add("None");
        for (Pokemon.Type t : Pokemon.getAllTypes()) {
            types.add(Pokemon.typeToString(t));
        }

        ArrayList<String> gens = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            gens.add("" + i);
        }


        typeBox.setItems(FXCollections.observableArrayList(types));
        typeBox.setValue("None");
        type2Box.setItems(FXCollections.observableArrayList(types));
        type2Box.setValue("None");
        generationBox.setItems(FXCollections.observableArrayList(gens));
        generationBox.setValue("0");

        typeBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searches.set(0, newValue);
                updateList();
            }
        });

        type2Box.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searches.set(1, newValue);
                updateList();
            }
        });

        fullyEvolvedBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    searches.set(2,"TRUE");
                } else {
                    searches.set(2, "FALSE");
                }
                updateList();
            }
        });

        megaEvoBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    searches.set(3, "TRUE");
                } else {
                    searches.set(3, "FALSE");
                }
                updateList();
            }
        });

        generationBox.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searches.set(4, newValue);
                updateList();
            }
        });
    }

    private void updateList() {
        ObservableList<Pokemon> searched = FXCollections.observableArrayList();
        for (Pokemon p: orig) {
            if (searches.get(3).equals("FALSE")) {
                if (searches.get(2).equals("FALSE")) {
                    if (searches.get(0).equals("None") && searches.get(1).equals("None")) {
                        if (searches.get(4).equals("0")) {
                            searched = orig;
                        } else {
                            if (p.getGeneration().equals(searches.get(4))) {
                                searched.add(p);
                            }
                        }
                    } else if (searches.get(1).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else if (searches.get(0).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && (p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    }
                } else {
                    if (searches.get(0).equals("None") && searches.get(1).equals("None")) {
                        if (p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                            if (!searches.get(4).equals("0")) {
                                if (p.getGeneration().equals(searches.get(4))) {
                                    searched.add(p);
                                }
                            } else {
                                searched.add(p);
                            }
                        }
                    } else if (searches.get(1).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else if (searches.get(0).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else {
                        if (p.getTypes().size() == 2) {

                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && (p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    }
                }
            } else {
                if (searches.get(2).equals("FALSE")) {
                    if (searches.get(0).equals("None") && searches.get(1).equals("None")) {
                        if (p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3))) {
                            if (!searches.get(4).equals("0")) {
                                if (p.getGeneration().equals(searches.get(4))) {
                                    searched.add(p);
                                }
                            } else {
                                searched.add(p);
                            }
                        }
                    } else if (searches.get(1).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else if (searches.get(0).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && (p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    }
                } else {
                    if (searches.get(0).equals("None") && searches.get(1).equals("None")) {
                        if (p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2)) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3))) {
                            if (!searches.get(4).equals("0")) {
                                if (p.getGeneration().equals(searches.get(4))) {
                                    searched.add(p);
                                }
                            } else {
                                searched.add(p);
                            }
                        }
                    } else if (searches.get(1).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2)) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3)))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else if (searches.get(0).equals("None")) {
                        if (p.getTypes().size() == 2) {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2)) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        } else {
                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3)))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    } else {
                        if (p.getTypes().size() == 2) {

                            if ((p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(0))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(0))))
                                    && (p.getTypes().get(0).equals(Pokemon.stringToType(searches.get(1))) || p.getTypes().get(1).equals(Pokemon.stringToType(searches.get(1))))
                                    && p.getFullyEvolved() == Pokemon.stringToBool(searches.get(2)) && p.getMegaEvolved() == Pokemon.stringToBool(searches.get(3))) {
                                if (!searches.get(4).equals("0")) {
                                    if (p.getGeneration().equals(searches.get(4))) {
                                        searched.add(p);
                                    }
                                } else {
                                    searched.add(p);
                                }
                            }
                        }
                    }
                }
            }

        }
        pokemonView.setItems(searched);
        pokemonView.getSelectionModel().select(0);
        pokemonView.getFocusModel().focus(0);
    }

    @FXML
    private void sortByName(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
    }

    @FXML
     private void sortByNumber(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p1.getId() - p2.getId();
            }
        });
    }

    @FXML
    private void sortByHP(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getHP() - p1.getHP();
            }
        });
    }

    @FXML
    private void sortByATK(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getATK() - p1.getATK();
            }
        });
    }

    @FXML
    private void sortByDEF(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getDEF() - p1.getDEF();
            }
        });
    }

    @FXML
    private void sortBySPA(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getSPA() - p1.getSPA();
            }
        });
    }

    @FXML
    private void sortBySPD(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getSPD() - p1.getSPD();
            }
        });
    }

    @FXML
    private void sortBySPE(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getSPE() - p1.getSPE();
            }
        });
    }

    @FXML
    private void sortByT0T(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getTOT() - p1.getTOT();
            }
        });
    }

    @FXML
    private void sortByAVG(ActionEvent event) {
        pokemonView.getItems().sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getAVG() - p1.getAVG();
            }
        });
    }

    @FXML
     private void openSerebii(ActionEvent event) throws Exception{
        String url = pokemonView.getSelectionModel().getSelectedItem().getSerebii();
        URI u = new URI(url);
        java.awt.Desktop.getDesktop().browse(u);
    }


    @FXML
    private void openSmogon(ActionEvent event) throws Exception{
        String url = pokemonView.getSelectionModel().getSelectedItem().getSmogon();
        URI u = new URI(url);
        java.awt.Desktop.getDesktop().browse(u);
    }

    public ArrayList<Pokemon> generator(File csv) {
        ArrayList<Pokemon> pkmnArrayList = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        int i = 0;

        try {

            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                String name = info[0];
                int id = Integer.parseInt(info[1]);
                ArrayList<Pokemon.Type> types = new ArrayList<>();
                types.add(Pokemon.stringToType(info[2]));
                if (!info[3].equals("")) { types.add(Pokemon.stringToType(info[3]));}
                boolean fullyEvo = Pokemon.stringToBool(info[4]);
                boolean megaEvo = Pokemon.stringToBool(info[5]);
                int hp = Integer.parseInt(info[6]);
                int atk = Integer.parseInt(info[7]);
                int def = Integer.parseInt(info[8]);
                int spa = Integer.parseInt(info[9]);
                int spd = Integer.parseInt(info[10]);
                int spe = Integer.parseInt(info[11]);
                int tot = Integer.parseInt(info[12]);
                int avg = Integer.parseInt(info[13]);

                Pokemon pokemon = new Pokemon(name, id, types, fullyEvo, megaEvo,
                        hp, atk, def, spa, spd, spe, tot, avg);

                pkmnArrayList.add(i, pokemon);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // for (int j = 0; j < 9; j++) {
        //     System.out.println(pkmnArrayList[j]);
        // }
        return pkmnArrayList;
    }
}
