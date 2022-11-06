# JeroKit

[![](https://jitpack.io/v/Debdutta-Panda/JeroKit.svg)](https://jitpack.io/#Debdutta-Panda/JeroKit)

![jerokit_logo](https://user-images.githubusercontent.com/92369023/200164684-e6e75ffd-9e09-4cf0-936d-99ceec59962a.svg)

Jetpack Compose is just awesome. It saves lots of hours and produce nice UI with minimal effort. But few things like:
- Navigation
- Modal bottom sheet handling
- Permission request and check
- Resulting activity handling like camera, gallery etc
- Back press handling
- Soft input mode management
- UI reusability across multiple viewModels

are still weird because they need to be handled directly from composable UI code, but viewModel need to handle these things.

Jerokit offers very easy mechanisms to manage these things directly from viewModel.

# Installation

Add it in your root build.gradle at the end of repositories:
```Groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```Groovy
dependencies {
    implementation 'com.github.Debdutta-Panda:JeroKit:<latest_version>'
}
```
# Usage
--------------------------
```Kotlin
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JeroApp() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController,
        startDestination = "main"
    ) {
        JeroScreen<MainViewModel>(
            navController = navController,
            route = "main",
        ) {
            MainScreen()
        }
        JeroScreen<PageAViewModel>(
            navController = navController,
            route = "pageA",
        ) {
            PageA()
        }
    }
}
```
Every screen is must to use with viewModel. So this kit is essentially force you to follow MVVM.
MainScreen() and PageA() are normal/regular composables. You can include as many pages as you want.
## NavHostController
-----------------------
```Kotlin
val navController = rememberAnimatedNavController()
AnimatedNavHost(
    navController,
    startDestination = "main"
) {
    // your screens
}
```
This kit force you to use Animated NavHost.
## Screen
-----------------------
```Kotlin
JeroScreen<your_view_model>(
    navController = navController,
    route = "your_route",
) {
    // your composable
}
```
### Example screen content
```Kotlin
@Composable
fun MainScreen(
    notifier: NotificationService = myNotifier()
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ){
        item{
            Button(onClick = {
                notifier.notify("goto_a")
            }) {
                Text("Go to PageA")
            }
        }

    }
}
```
### Example viewModel
```Kotlin
class MainViewModel: WirelessViewModelInterface, ViewModel() {
    override val softInputMode = mutableStateOf(SoftInputMode.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val notifier: NotificationService = NotificationService{id,arg->
        
    }
    override val navigation: MutableState<UIScope?> = Navigation()
    override val permissionHandler: PermissionHandler = PermissionHandler()
    override val resultingActivityHandler: ResultingActivityHandler = ResultingActivityHandler()
}
```
## Navigation

Let's suppose we need to goto pageA when MainScreen's one button will be clicked. 
In that case the MainViewModel will look like below:
```Kotlin
class MainViewModel: WirelessViewModelInterface, ViewModel() {
    override val softInputMode = mutableStateOf(SoftInputMode.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val notifier: NotificationService = NotificationService{id,arg->
        when(id) {
            "goto_a"->{
                navigation.scope { navHostController, lifecycleOwner, toaster ->
                    navHostController.navigate("pageA")
                }
            }
        }
    }
    override val navigation: MutableState<UIScope?> = Navigation()
    override val permissionHandler: PermissionHandler = PermissionHandler()
    override val resultingActivityHandler: ResultingActivityHandler = ResultingActivityHandler()
}
```
Inside the `navigation.scope` block you can do any in-built navigation action you want. JeroKit gives you full freedom to use navigation api.
# Notifier

```Kotlin
@Composable
fun MainScreen(
    notifier: NotificationService = myNotifier()
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ){
        item{
            Button(onClick = {
                notifier.notify("goto_a")
            }) {
                Text("Go to PageA")
            }
        }

    }
}
```
It is to notify viewModel about any UI action/interaction/event etc.

`notifier.notify` takes 2 arguments. First is id, second is for data, it is optional. Id ardument will take any non-null thing.

The notification will be tracked inside `override val notifier: NotificationService = NotificationService block`
```Kotlin
override val notifier: NotificationService = NotificationService{id,arg->
        when(id) {
            "your_id"->{
                // do whatever you want, arg can be null as it is optional
            }
        }
    }
```
# Screen Content from viewModel

```Kotlin
@Composable
fun MainScreen(
    myText: String = stringState(key = "myText").value,
    notifier: NotificationService = myNotifier()
){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ){
        item{
            Text(myText)
        }
        item{
            Button(onClick = {
                notifier.notify("change")
            }) {
                Text("Change")
            }
        }
    }
}
```
```Kotlin
class MainViewModel: WirelessViewModelInterface, ViewModel() {
    override val softInputMode = mutableStateOf(SoftInputMode.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val notifier: NotificationService = NotificationService{id,arg->
        when(id) {
            "change"->{
                myText.value = "${++count}"
            }
        }
    }
    override val navigation: MutableState<UIScope?> = Navigation()
    override val permissionHandler: PermissionHandler = PermissionHandler()
    override val resultingActivityHandler: ResultingActivityHandler = ResultingActivityHandler()
    ////////////////////////////////////////////////////////////////
    private var count = 0
    private val myText = mutableStateOf("$count")
    ////////////////////////////////////////////////////////////////
    init {
        resolver.addAll(
            "myText" to myText,
        )
    }
}
```

# Resolver

It maps ids to data. UI request data via that id. You can add any number of uniqe mappings.