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
- Status bar color management
- UI reusability across multiple viewModels

are still weird because they need to be handled directly from composable UI code, but viewModel need to handle these things.

Jerokit offers very easy mechanisms to manage these things directly from viewModel.

# Demo

https://user-images.githubusercontent.com/92369023/200178078-f75a9e6f-d06c-475b-a495-ea9327067a51.mp4

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

# Back press handling

Back press event will be notified via `NotificationService`.

```Kotlin
override val notifier: NotificationService = NotificationService{id,arg->
        when(id){
            "back_<route_name>"->{
                navigation.scope { navHostController, lifecycleOwner, toaster ->
                    //navHostController.popBackStack() // or whatever you want
                }
            }
        }
    }
```

# Status bar color management

```Kotlin
Button(onClick = {
    notifier.notify("change_color")
}) {
    Text("StatusBarColor")
}
```
```Kotlin
/////// in viewModel
var obj: Random = Random()
val colors = listOf(
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Magenta,
    Color.Cyan,
    Color.White,
    Color.Black,
    Color.Gray,
    Color.DarkGray,
    Color.LightGray,
)
val statusBarColor = mutableStateOf<StatusBarColor?>(null)
/////////////// inside notification service block
"change_color"->{
    obj.setSeed(System.currentTimeMillis())
    val rand_num: Int = obj.nextInt(colors.size - 1)
    val color = colors[rand_num]
    statusBarColor.value = StatusBarColor(
        color = color,
        darkIcons = true
    )
}
/*
    inside init block resolver.addAll(...)
*/
StatusBarColorId to statusBarColor
```

# Soft input mode management

```Kotlin
Column {
    Row(){
        Button(onClick = {
            notifier.notify("softInputMode",0)
        }) {
            Text("Pan")
        }
        Button(onClick = {
            notifier.notify("softInputMode",1)
        }) {
            Text("Nothing")
        }
        Button(onClick = {
            notifier.notify("softInputMode",2)
        }) {
            Text("Unspecified")
        }
        Button(onClick = {
            notifier.notify("softInputMode",3)
        }) {
            Text("Resize")
        }
    }
    Box(modifier =Modifier.height(400.dp))
    TextField(value = "", onValueChange = {})
}
```
```Kotlin
"softInputMode"->{
    when(arg){
        0->softInputMode.value = SoftInputMode.adjustPan
        1->softInputMode.value = SoftInputMode.adjustNothing
        2->softInputMode.value = SoftInputMode.adjustUnspecified
        3->softInputMode.value = SoftInputMode.adjustResize
    }
}
```

# UI reusability across multiple viewModels

Because UI has not any viewModel instance access, it is getting data via resolver and sending UI events through notifier it is fully decoupled from viewModel instance. So any ui can be attached to any viewModel, that viewModel must satisfy the UI data requirement otherwise app will crash, it is by design.

# Permission management

```Kotlin
Column(){
    Row(){
        Button(onClick = {
            notifier.notify("check_permission")
        }) {
            Text("CheckPermission")
        }
        Button(onClick = {
            notifier.notify("request_permission")
        }) {
            Text("RequestPermission")
        }
    }
    Text(myPermissionCheck)
}
```
```Kotlin
"check_permission"->{
    viewModelScope.launch(Dispatchers.Main) {
        val result = permissionHandler.check(android.Manifest.permission.CAMERA)
        val granted = result?.allPermissionsGranted==true
        myPermissionCheck.value = "CheckResult: $granted"
    }
}
"request_permission"->{
    viewModelScope.launch(Dispatchers.Main) {
        val result = permissionHandler.request(android.Manifest.permission.CAMERA)
        val granted = result?.get(android.Manifest.permission.CAMERA)==true
        myPermissionCheck.value = "RequestResult: $granted"
    }
}
```

# Resulting activity handle

## Example: Take picture preview from camera
-------------------------------

```Kotlin
Column {
    AsyncImage(
        model = image,
        contentDescription = ""
    )
    Button(onClick = {
        notifier.notify("image")
    }) {
        Text("Take Image")
    }
}
```
```Kotlin
"image"->{
    viewModelScope.launch(Dispatchers.Main) {
        val result = resultingActivityHandler.takePicturePreview()
        withContext(Dispatchers.Main){
            image.value = result
        }
    }
}
```

# Modal Bottom Sheet management

```Kotlin
Row(){
    Button(onClick = {
        notifier.notify("sheet",1)
    }) {
        Text("Sheet1")
    }
    Button(onClick = {
        notifier.notify("sheet",2)
    }) {
        Text("Sheet2")
    }
}
```
```Kotlin
"sheet"->{
    when(arg){
        1->{
            mySheeting.change(MySheets.Sheet1)
            mySheeting.show()
        }
        2->{
            mySheeting.change(MySheets.Sheet2)
            mySheeting.show()
        }
    }
}
```
```Kotlin
@Composable
fun Sheet1(
    text: String = stringState(key = "text").value,
    text1: String = stringState(key = "text1").value,
    text2: String = stringState(key = "text2").value,
    notifier: NotificationService = myNotifier()
){
    SheetColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
        ,
        verticalArrangement = Arrangement.Center,
    ){
        Text("Sheet1")
        Text(text)
        TextField(
            value = text1,
            onValueChange = {
                notifier.notify("text1",it)
            }
        )
        TextField(
            value = text2,
            onValueChange = {
                notifier.notify("text2",it)
            }
        )
        Row(){
            Button(onClick = {
                notifier.notify("button")
            }) {
                Text("Join")
            }
            Button(onClick = {
                notifier.notify("done")
            }) {
                Text("Done")
            }
            Button(onClick = {
                notifier.notify("switch")
            }) {
                Text("Switch")
            }
        }
    }
}
```
```Kotlin
class Sheet1Model(val callback: Callback): BottomSheetModel {
    interface Callback{
        fun scope(): CoroutineScope
        fun close()
        fun switch()
    }
    /////////////////
    private val _resolver = Resolver()
    private val _notifier = NotificationService{id,arg->
        when(id){
            "text1"->{
                text1.value = arg as? String?:return@NotificationService
            }
            "text2"->{
                text2.value = arg as? String?:return@NotificationService
            }
            "button"->{
                text.value = "${text1.value} and ${text2.value}"
            }
            "done"->{
                callback.close()
            }
            "switch"->{
                callback.switch()
            }
        }
    }
    /////////////////
    override val resolver = _resolver
    override val notifier = _notifier
    override val scope get() = callback.scope()

    @Composable
    override fun Content() {
        Sheet1()
    }

    override fun initialize() {

    }

    override fun clear() {
        text.value = ""
        text1.value = ""
        text2.value = ""
    }

    override fun onBack() {
        callback.close()
    }

    override fun onVisibilityChange(it: Boolean) {
        if(!it){
            clear()
        }
    }

    ////////////
    private val text = mutableStateOf("")
    private val text1 = mutableStateOf("")
    private val text2 = mutableStateOf("")
    ////////////
    init {
        _resolver.addAll(
            "text" to text,
            "text1" to text1,
            "text2" to text2
        )
    }
}
```
```Kotlin
@Composable
fun Sheet2(
    text: String = stringState(key = "text").value,
    text1: String = stringState(key = "text1").value,
    text2: String = stringState(key = "text2").value,
    notifier: NotificationService = myNotifier()
){
    SheetColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ){
        Text("Sheet2")
        Text(text)
        TextField(
            value = text1,
            onValueChange = {
                notifier.notify("text1",it)
            }
        )
        TextField(
            value = text2,
            onValueChange = {
                notifier.notify("text2",it)
            }
        )
        Row(){
            Button(onClick = {
                notifier.notify("button")
            }) {
                Text("Join")
            }
            Button(onClick = {
                notifier.notify("done")
            }) {
                Text("Done")
            }
            Button(onClick = {
                notifier.notify("switch")
            }) {
                Text("Switch")
            }
        }
    }
}
```
```Kotlin
class Sheet2Model(val callback: Callback): BottomSheetModel {
    interface Callback{
        fun scope(): CoroutineScope
        fun close()
        fun switch()
    }
    /////////////////
    private val _resolver = Resolver()
    private val _notifier = NotificationService{id,arg->
        when(id){
            "text1"->{
                text1.value = arg as? String?:return@NotificationService
            }
            "text2"->{
                text2.value = arg as? String?:return@NotificationService
            }
            "button"->{
                text.value = "${text1.value} or ${text2.value}"
            }
            "done"->{
                callback.close()
            }
            "switch"->{
                callback.switch()
            }
        }
    }
    /////////////////
    override val resolver = _resolver
    override val notifier = _notifier
    override val scope get() = callback.scope()

    @Composable
    override fun Content() {
        Sheet2()
    }

    override fun initialize() {

    }

    override fun clear() {
        text.value = ""
        text1.value = ""
        text2.value = ""
    }

    override fun onBack() {
        callback.close()
    }

    override fun onVisibilityChange(it: Boolean) {
        clear()
    }

    ////////////
    private val text = mutableStateOf("")
    private val text1 = mutableStateOf("")
    private val text2 = mutableStateOf("")
    ////////////
    init {
        _resolver.addAll(
            "text" to text,
            "text1" to text1,
            "text2" to text2
        )
    }
}
```

In viewModel

```Kotlin
override val sheeting = Sheeting(
        sheetMap = mapOf(
            MySheets.Sheet1 to Sheet1Model(
                object: Sheet1Model.Callback{
                    override fun scope(): CoroutineScope {
                        return viewModelScope
                    }

                    override fun close() {
                        mySheeting.hide()
                    }

                    override fun switch() {
                        mySheeting.change(MySheets.Sheet2)
                    }
                }
            ),
            MySheets.Sheet2 to Sheet2Model(
                object: Sheet2Model.Callback{
                    override fun scope(): CoroutineScope {
                        return viewModelScope
                    }

                    override fun close() {
                        mySheeting.hide()
                    }

                    override fun switch() {
                        mySheeting.change(MySheets.Sheet1)
                    }
                }
            )
        )
    )
```

The bottom sheet manangement mechanism supports multiple modal bottom sheets in the same page/screen. The sheets can be used in any viewModel. They are pluggable in nature.
