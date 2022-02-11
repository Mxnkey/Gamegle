package com.siw.query;

import com.siw.model.VideoGame;
import org.apache.jena.query.*;
import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.RDFNode;

import java.awt.desktop.PreferencesEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SparqlQuery {

    public static VideoGame getVideoGameDetails(String videoGame){

        String nameRessource = "?name";
        String editorRessource = "?editeur";
        String platformRessource = "?platforms";
        String kindRessource = "?genre";

        String getVideoGameName = // 1 value
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "\n" +
                "select ?name  where {\n" +
                "?s rdfs:label ?name .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(?s in (:" + videoGame +") && lang(?name)='en')\n" +
                "} ";


        String getVideoGameEditor = // 1 value
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "\n" +
                "select ?editeur  where {\n" +
                "?s dbp:publisher ?editeur .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(?s in (:" + videoGame +"))\n" +
                "} ";

        String getVideoGamePlatforms = // x values
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "\n" +
                "select ?platforms  where {\n" +
                "?s dbo:computingPlatform ?platforms.\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(?s in (:" + videoGame +"))\n" +
                "} ";

        String getVideoGameKinds = // 1 value
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "\n" +
                "select ?genre where {\n" +
                "?s dbo:genre ?genre .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(?s in (:" + videoGame +"))\n" +
                "} ";

        List<QuerySolution> nameResult = sparqlQueryResultWithOneRessource(getVideoGameName, nameRessource);
        List<QuerySolution> editorResult = sparqlQueryResultWithOneRessource(getVideoGameEditor, editorRessource);
        List<QuerySolution> platformsResult = sparqlQueryResultWithOneRessource(getVideoGamePlatforms, platformRessource);
        List<QuerySolution> kindResult = sparqlQueryResultWithOneRessource(getVideoGameKinds, kindRessource);

        VideoGame myVideoGame = new VideoGame();

        if(nameResult.size() == 1){
            myVideoGame.setName(rdfNodeToString(nameResult.get(0).get(nameRessource)));
        }

        if(editorResult.size() == 1){
            String ressourceLink[] = rdfNodeToString(editorResult.get(0).get(editorRessource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
            myVideoGame.setEditor(resultRessource);
        }

        if(kindResult.size() ==1){
            String ressourceLink[] = rdfNodeToString(kindResult.get(0).get(kindRessource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
            myVideoGame.setKind(resultRessource);
        }

        if(!platformsResult.isEmpty()){
            for (QuerySolution result : platformsResult) {
                //On récupère seulement le nom de la ressource
                String ressourceLink[] = rdfNodeToString(result.get(platformRessource)).split("/");
                String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
                myVideoGame.getPlatforms().add(resultRessource);
            }
        }

        return myVideoGame;
    }

    public static ArrayList<String> getVideoGamesByEditors(String editor){

        String ressource = "?VideoGame";
        String getVideoGamesByEditor =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "select distinct ?VideoGame ?editeur where {\n" +
                "?VideoGame a dbo:VideoGame .\n" +
                "?VideoGame a dbo:Software .\n" +
                "?VideoGame dbp:publisher ?editeur .\n" +
                "FILTER(?editeur in (:" + editor +"))\n" +
                "} ";

        List<QuerySolution> videoGames = sparqlQueryResultWithOneRessource(getVideoGamesByEditor, ressource);
        ArrayList<String> videoGamesList = new ArrayList<String>();

        for (QuerySolution result : videoGames) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
            videoGamesList.add(resultRessource);
        }
        return videoGamesList;
    }


    public static ArrayList<String> getVideoGamesByPlatform(String platform) {
        String ressource = "?VideoGame";
        String getVideoGamesByPlatform =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "select distinct ?VideoGame ?platform where {\n" +
                "?VideoGame a dbo:VideoGame .\n" +
                "?VideoGame a dbo:Software .\n" +
                "?VideoGame dbo:computingPlatform ?platform .\n" +
                "FILTER(?platform in (:" + platform +"))\n" +
                "} ";

        List<QuerySolution> videoGames = sparqlQueryResultWithOneRessource(getVideoGamesByPlatform, ressource);
        ArrayList<String> videoGamesList = new ArrayList<String>();

        for (QuerySolution result : videoGames) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
            videoGamesList.add(resultRessource);
        }
        return videoGamesList;
    }

    public static String getVideoGamesByName(String filter) {

        String ressource = "?s";

        String getVideoGameByName = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "\n" +
                "select ?s where {\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(?s in (:" + filter +"))\n" +
                "}";

        List<QuerySolution> nameResult = sparqlQueryResultWithOneRessource(getVideoGameByName, ressource);
        String name = null;

        if(nameResult.size() == 1){
            String ressourceLink[] = rdfNodeToString(nameResult.get(0).get(ressource)).split("/");
            name = ressourceLink[ressourceLink.length - 1];
        }

        return name;

    }

    public static ArrayList<String> getVideoGamesByKind(String kind){

        String ressource = "?VideoGame";
        String getVideoGamesByKind =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "select distinct ?VideoGame ?genre where {\n" +
                "?VideoGame a dbo:VideoGame .\n" +
                "?VideoGame a dbo:Software .\n" +
                "?VideoGame dbo:genre ?genre .\n" +
                "FILTER(?genre in (:" + escapeSpecialChar(kind) +"))\n" +
                "} ";

        List<QuerySolution> videoGames = sparqlQueryResultWithOneRessource(getVideoGamesByKind, ressource);
        ArrayList<String> videoGamesList = new ArrayList<String>();

        for (QuerySolution result : videoGames) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length - 1]; //Nom de la plateforme
            videoGamesList.add(resultRessource);
        }
        return videoGamesList;
    }

    public static ArrayList<String> getVideoGamesEditors(){

        String ressource1 = "?editeur";
        String ressource2 = "?count";

        String getVideoGamesEditors =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "PREFIX dbp: <http://dbpedia.org/property/> \n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "select distinct ?editeur (count(?editeur) as ?count) where {\n" +
                "?s dbp:publisher ?editeur .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "FILTER(regex(?editeur, \":\" ))\n" +
                "} \n" +
                "GROUP BY ?editeur\n" +
                "ORDER BY DESC(?count)\n";

        List<QuerySolution> videoGamesEditors = sparqlQueryResultWithTwoRessources(getVideoGamesEditors, ressource1,ressource2);
        ArrayList<String> editorsList = new ArrayList<String>();

        for (QuerySolution result : videoGamesEditors) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource1)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length-1]; //Nom de la plateforme


            //On récupère seulement l'entier
            String countLink[] = rdfNodeToString(result.get(ressource2)).split("\\^");
            Integer count = Integer.parseInt(countLink[0]);

            if(count > 200){
                editorsList.add(resultRessource);
            }

        }

        return editorsList;
    }

    public static ArrayList<String> getVideoGamesPlatform(){ //On récupère les 50 premier
        String ressource1 = "?platform";
        String ressource2 = "?count";

        String getVideoGamesPlatform =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "select distinct ?platform (count(?platform) as ?count) where {\n" +
                "?s dbo:computingPlatform ?platform .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "}\n" +
                "GROUP BY ?platform\n" +
                "ORDER BY DESC(?count)\n";

        List<QuerySolution> videoGamesPlatform = sparqlQueryResultWithTwoRessources(getVideoGamesPlatform, ressource1,ressource2);
        ArrayList<String> platformsList = new ArrayList<String>();

        for (QuerySolution result : videoGamesPlatform) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource1)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length-1]; //Nom de la plateforme


            //On récupère seulement l'entier
            String countLink[] = rdfNodeToString(result.get(ressource2)).split("\\^");
            Integer count = Integer.parseInt(countLink[0]);

            if(count > 200){
                platformsList.add(resultRessource);
            }

        }

        return platformsList;
    }

    public static ArrayList<String> getKindOfVideoGames(){

        String ressource1 = "?genre";
        String ressource2 = "?count";

        String getKindOfVideoGames =
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  \n" +
                "select distinct ?genre (count(?genre) as ?count) where {\n" +
                "?s dbo:genre ?genre .\n" +
                "?s a dbo:VideoGame .\n" +
                "?s a dbo:Software .\n" +
                "}"+
                "GROUP BY ?genre\n" +
                "ORDER BY DESC(?count)\n";;

        List<QuerySolution> kindOfGames = sparqlQueryResultWithTwoRessources(getKindOfVideoGames,ressource1, ressource2);
        ArrayList<String> kindOfGamesList = new ArrayList<String>();

        for (QuerySolution result : kindOfGames) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource1)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length-1]; //Nom de la plateforme


            //On récupère seulement l'entier
            String countLink[] = rdfNodeToString(result.get(ressource2)).split("\\^");
            Integer count = Integer.parseInt(countLink[0]);

            if(count > 200){
                kindOfGamesList.add(resultRessource);
            }

        }

        return kindOfGamesList;
    }

    public static ArrayList<String> getAllVideoGames(){

        String ressource = "?VideoGame";
        String getAllVideoGames =
                "PREFIX dbo: <http://dbpedia.org/ontology/> " +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "   SELECT ?VideoGame" +
                "   WHERE { " +
                "       ?VideoGame a dbo:Software ." +
                "       ?VideoGame a dbo:VideoGame ." +
                "   }";

        List<QuerySolution> allVideoGames = sparqlQueryResultWithOneRessource(getAllVideoGames, ressource);
        ArrayList<String> videoGamesList = new ArrayList<String>();

        for (QuerySolution result : allVideoGames) {
            //On récupère seulement le nom de la ressource
            String ressourceLink[] = rdfNodeToString(result.get(ressource)).split("/");
            String resultRessource = ressourceLink[ressourceLink.length-1]; //Nom de la plateforme
            videoGamesList.add(resultRessource);
        }

        return videoGamesList;
    }

    public static List<QuerySolution> sparqlQueryResultWithOneRessource(String sparqlQueryString, String ressource){
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = qexec.execSelect();
        List<QuerySolution> resultList = ResultSetFormatter.toList(results);
        qexec.close();
        return resultList;
    }

    public static List<QuerySolution> sparqlQueryResultWithTwoRessources(String sparqlQueryString, String ressource1, String ressource2){
        Query query = QueryFactory.create(sparqlQueryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = qexec.execSelect();
        List<QuerySolution> resultList = ResultSetFormatter.toList(results);
        qexec.close();
        return resultList;
    }

    private static String rdfNodeToString(RDFNode node){
        if (node.isLiteral()) {
            return node.asLiteral().getString();
        } else if (node.isResource()) {
            return node.asResource().getURI();
        }
        return null;
    }

    private static String escapeSpecialChar(String myString){

        myString.replace("&", "\\&");
        myString.replace("'", "\\'");
        myString.replace("%","\\%");
        myString.replace("(","\\(");
        myString.replace(")","\\)");

        return myString;
    }


}
