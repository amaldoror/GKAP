### HAW HH - BAI3 - GKAP-04 SoSe 2024

# Graphentheoretische Konzepte & Algorithmen Praktikum

## Aufgabe 01


> <b><u>Der Breadth-First-Search (BFS) Algorithmus</u></b> funktioniert unterschiedlich je nach Art des Graphen:
> 
> - <u>Ungewichtete, ungerichtete Graphen: </u><br>
> BFS funktioniert gut für ungewichtete, ungerichtete Graphen.
> Da die Kanten keine Richtung haben und keine Gewichte tragen, besucht BFS die Knoten in der Reihenfolge ihrer Entfernung vom Startknoten.
> Er findet den kürzesten Pfad von einem Startknoten zu allen anderen Knoten im Graphen.
>- <u>Ungewichtete, gerichtete Graphen:</u><br>
> Auch hier funktioniert BFS gut. Die Richtung der Kanten spielt keine Rolle, solange sie ungewichtet sind.
> Der Algorithmus besucht alle erreichbaren Knoten ausgehend vom Startknoten und findet den kürzesten Pfad zu allen anderen Knoten.
> - <u>Gewichtete, ungerichtete Graphen: </u><br>
> BFS allein ist nicht optimal für gewichtete Graphen, da es keine Information über die Kosten der Kanten berücksichtigt.
> Der Algorithmus besucht zwar alle erreichbaren Knoten, aber er berücksichtigt nicht zwangsläufig den kürzesten Pfad, da er die Kanten nicht nach Gewicht priorisiert.
> - <u>Gewichtete, gerichtete Graphen: </u><br>
> Ähnlich wie bei gewichteten, ungerichteten Graphen ist BFS allein nicht die beste Wahl für gewichtete, gerichtete Graphen.
> Er kann verwendet werden, um alle erreichbaren Knoten zu finden, aber er berücksichtigt nicht die Gewichtung der Kanten, was bedeutet, dass er möglicherweise nicht den kürzesten Pfad von einem Startknoten zu anderen Knoten findet.
>  
> Für gewichtete Graphen, ob gerichtet oder ungerichtet, werden oft Algorithmen wie Dijkstra oder der A*-Algorithmus verwendet, um den kürzesten Pfad zwischen zwei Knoten zu finden, da sie die Kantengewichte berücksichtigen.