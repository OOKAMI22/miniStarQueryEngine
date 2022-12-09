package qengine.program;
import org.apache.commons.cli.*;
public class JarCommander {
    public static void main(String[] args) throws Exception {
        CommandLine cmd = getCommandLine(args, new Options(), new DefaultParser()) ;

        String queries = cmd.getOptionValue("queries") ;
        String data = cmd.getOptionValue("data") ;
        String output = cmd.getOptionValue("output") ;
        boolean jena = cmd.hasOption("Jena") ;
        String warm = cmd.getOptionValue("warm") ;
        boolean shuffle = cmd.hasOption("shuffle") ;

        if (queries != null) { Main.queryFile = queries; }
        if (data != null) { Main.dataFile = data; }
        if (output != null) { Main.outputFile = output; }

        if (jena) { Main.jena = true; } // Jena
        if (warm != null) { Main.warm = Integer.parseInt(warm);}
        if (shuffle) { Main.shuffle = true;}

        System.err.println("WorkindDir = " + Main.workingDir);
        System.out.println("QueryFile = " + Main.queryFile);
        System.out.println("DataFile = " + Main.dataFile);
        System.out.println("OutputFile = " + Main.output);
        System.out.println("Jena = " + Main.jena);
        System.out.println("Warm = " + Main.warm);
        System.out.println("Shuffle = " + Main.shuffle);

        Main.main(args);
    }


    /**
     * Retourne La ligne de commande parsée
     * @param args : Les arguments de la commande
     * @param options : Un objet contenant les options a prendre en compte
     * @param parser : Quel parseur utiliser
     * @return CommandLine : Un objet representant la ligne de commande
     * @throws ParseException
     */
    public static CommandLine getCommandLine(String[] args, Options options, CommandLineParser parser) throws ParseException {
        options.addOption("queries", true, "/chemin/vers/dossier/requetes");
        options.addOption("data", true, "/chemin/vers/fichier/donnees");
        options.addOption("output", true, "/chemin/vers/dossier/sortie");
        options.addOption("Jena", false, "Active la vérification de la correction et complétude du système en utilisant Jena comme un oracle");
        options.addOption("warm", true, "Utilise un échantillon des requêtes en entrée (prises au hasard) correspondant au pourcentage \"X\" pour chauffer le système");
        options.addOption("shuffle", false, "Considère une permutation aléatoire des requêtes en entrée");

        return parser.parse(options, args);
    }

}
