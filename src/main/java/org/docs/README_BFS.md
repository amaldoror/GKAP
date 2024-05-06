# Breadth-First-Search Algorithm

### Zeitkomplexität

Angenommen, der Graph hat n Knoten und m Kanten.

<b>Initialisierung:</b> Die Initialisierung des BFS erfordert in der Regel <code>O(n)</code> Operationen, da jeder Knoten des Graphen einmal besucht und markiert werden muss. <br>
<b>Durchlaufen der Kanten:</b> Für jeden Knoten werden alle inzidenten Kanten besucht. Da jede Kante maximal zweimal besucht wird (einmal von jedem Endknoten aus), beträgt die Gesamtanzahl der Kantenbesuche <code>O(m)</code>. <br>
<b>Queue-Operationen:</b> Jeder Knoten wird einmal in die Queue eingefügt und einmal aus der Queue entfernt.
Da jede Kante maximal zweimal besucht wird, wird jeder Knoten höchstens einmal in die Queue eingefügt und entfernt.
Die Queue-Operationen haben somit eine Gesamtkomplexität von <code>O(n)</code>.

Insgesamt ergibt sich somit eine Zeitkomplexität von <code>O(n+m)</code> für den BFS-Algorithmus. <br>
In einem zusammenhängenden Graphen mit nn Knoten und mm Kanten gilt <code>m≥n−1</code>,
daher kann die Zeitkomplexität in der Regel als <code>O(m)</code> approximiert werden.

Es ist wichtig zu beachten, dass die tatsächliche Laufzeit des BFS-Algorithmus auch von Faktoren wie der Implementierungsdetails, der Wahl der Datenstruktur für die Queue und der Art der Markierung der besuchten Knoten abhängt.
