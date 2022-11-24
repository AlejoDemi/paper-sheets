package edu.austral.ingsis.starships

import Factory.duoGame
import Factory.soloGame
import adapter.Adapter
import edu.austral.ingsis.starships.ui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import model.State
import model.States
import utils.HEIGHT
import utils.LIVES
import utils.SPAWN_PROBS
import utils.WIDTH
import kotlin.system.exitProcess


fun main() {
    launch(Starships::class.java)
}

class Starships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()
    private var adapter = Adapter(
        State(
            States.PAUSE,
            listOf(),
            mapOf(),
            mapOf()

    ),SPAWN_PROBS)
    private var pressedKeys=listOf<KeyCode>()

    override fun start(primaryStage: Stage) {


        adapter = adapter.addElements(facade.elements)
        var displayMinutes: Long = 0
        var startTime = System.currentTimeMillis()
        val lives = StackPane()
        var lives1 = Label(LIVES.toString())
        var lives2 = Label(LIVES.toString())
        var time = Label("0.0")
        lives1.style= "-fx-font-family: VT323; -fx-font-size: 100"
        lives2.style= "-fx-font-family: VT323; -fx-font-size: 100"
        time.style= "-fx-font-family: VT323; -fx-font-size: 100"
        lives1.textFill = Color.color(0.9,0.9,0.9)
        lives2.textFill = Color.color(0.9,0.9,0.9)
        time.textFill = Color.color(0.9,0.9,0.9)
        val div1= HBox(50.0)
        val div2= HBox(50.0)
        div1.alignment= Pos.TOP_LEFT
        div2.alignment= Pos.TOP_CENTER
        div2.children.addAll(time)
        div1.children.addAll(lives1, lives2)
        div1.padding= Insets(10.0,10.0,10.0,10.0)
        div2.padding= Insets(10.0,10.0,10.0,10.0)
        lives.children.addAll(div1,div2)

        val pane=StackPane()
        val layout = VBox(100.0)

        val root = facade.view
        pane.children.addAll(root,lives)
        root.id = "pane"
        val scene = Scene(layout)
        keyTracker.scene = scene
        scene.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        scene.stylesheets.add("https://fonts.googleapis.com/css2?family=VT323&display=swap")
        primaryStage.scene = scene
        primaryStage.height = HEIGHT
        primaryStage.width = WIDTH

        //Initial Menu
        layout.alignment = Pos.CENTER
        layout.id = "pane"

        val name = Label("Paper-sheets")
        name.textFill = Color.BLACK
        name.style = "-fx-font-family: VT323; -fx-font-size: 150"

        val options = HBox(100.0)
        options.alignment = Pos.CENTER

        val onePlayer = Label("One Player")
        onePlayer.textFill = Color.BLACK
        onePlayer.style = "-fx-font-family: VT323; -fx-font-size: 80"
        onePlayer.setOnMouseEntered {
            onePlayer.textFill = Color.RED
            onePlayer.cursor = Cursor.HAND
        }
        onePlayer.setOnMouseExited {
            onePlayer.textFill = Color.BLACK
        }
        onePlayer.setOnMouseClicked {
            scene.root = pane
            adapter = Adapter(soloGame(), SPAWN_PROBS)
        }

        val twoPlayer = Label("Two Player")
        twoPlayer.textFill = Color.BLACK
        twoPlayer.style = "-fx-font-family: VT323; -fx-font-size: 80"
        twoPlayer.setOnMouseEntered {
            twoPlayer.textFill = Color.RED
            twoPlayer.cursor = Cursor.HAND
        }
        twoPlayer.setOnMouseExited {
            twoPlayer.textFill = Color.BLACK
        }
        twoPlayer.setOnMouseClicked {
            scene.root = pane
            adapter = Adapter(duoGame(), SPAWN_PROBS)
        }

        options.children.addAll(onePlayer, twoPlayer)
        layout.children.addAll(name, options)

        facade.start()
        facade.showCollider.set(false)
        keyTracker.start()
        primaryStage.show()

        facade.collisionsListenable.addEventListener(object : EventListener<Collision> {
            override fun handle(event: Collision) {
                adapter=adapter.collide(event.element1Id,event.element2Id,facade.elements).addElements(facade.elements)
            }
        })

        facade.timeListenable.addEventListener(object : EventListener<TimePassed> {
            override fun handle(event: TimePassed) {
                pressedKeys.forEach {adapter=adapter.keyPressed(it,event.secondsSinceLastTime)}
                if (adapter.state.gameState==States.PLAY){
                    adapter = adapter.keyFramePassed(event.secondsSinceLastTime).addElements(facade.elements).adaptElements(facade.elements)
                    lives1=adapter.updateLives("s1")
                    lives2=adapter.updateLives("s2")
                    val timePassed: Long = System.currentTimeMillis() - startTime
                    var secondsPassed = timePassed / 1000
                    if (secondsPassed >= 60L) {
                        startTime=System.currentTimeMillis()
                        secondsPassed = 0
                        displayMinutes+=1
                    }
                    time= Label(displayMinutes.toString()+":"+secondsPassed.toString())
                    lives1.style= "-fx-font-family: VT323; -fx-font-size: 100"
                    lives2.style= "-fx-font-family: VT323; -fx-font-size: 100"
                    time.style= "-fx-font-family: VT323; -fx-font-size: 100"
                    lives1.textFill = Color.color(0.9,0.9,0.9)
                    lives2.textFill = Color.color(0.9,0.9,0.9)
                    time.textFill = Color.color(0.9,0.9,0.9)
                    div1.children[0] = lives1
                    div1.children[1] = lives2
                    div2.children[0] = time
                    if (lives1.text=="" && lives2.text==""){
                        stop()
                        exitProcess(0)
                    }
                }else{
                    startTime=System.currentTimeMillis()
                }
            }
        })

        keyTracker.keyPressedListenable.addEventListener(object : EventListener<KeyPressed> {
            override fun handle(event: KeyPressed) {
                if(!pressedKeys.contains(event.key) && !adapter.state.releaseKeyMap.containsKey(event.key)){
                    pressedKeys=pressedKeys.plus(event.key)
                }
            }
        })

        keyTracker.keyReleasedListenable.addEventListener(object : EventListener<KeyReleased> {
            override fun handle(event: KeyReleased) {
                if (pressedKeys.contains(event.key)){
                    pressedKeys=pressedKeys.filter { it-> it!=event.key }
                }
                adapter = adapter.keyReleased(event).addElements(facade.elements)
            }
        })


    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}



