package com.instance.dataxbranch.ui


import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.data.local.UserWithAbilities
import com.instance.dataxbranch.ui.destinations.DefaultScreenDestination
import com.instance.dataxbranch.ui.destinations.TypedDestination


//import com.instance.dataxbranch.ui.destinations.Destination

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.constants.DEFAULT_UNAME
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
@RootNavGraph(start = true)
@Destination
@Composable
fun DefaultScreen(viewModel: UserViewModel = hiltViewModel(),
                    navigator: DestinationsNavigator) {


    val context = LocalContext.current
    Scaffold(



    ) { padding ->

        var me = viewModel.getMeWithAbilities()
        if (me.user.uname ==DEFAULT_UNAME && !me.user.initflag){viewModel.generalRepository.refresh()
            Log.d(TAG,"REPEATER")
            navigator.navigate(
                DefaultScreenDestination)}
        me = viewModel.getMeWithAbilities()

        val which = me.user.defaultScreen
        val dests = NavGraphs.root.destinations
        if (which>=0){
        navigator.navigate(
            dests[which].route)}

        Column{Text(me.user.uname)
            Text("GO TO USER SCREEN -> save")
            DefaultLazyColumn(navigator,context,viewModel, Screens = dests, modifier = Modifier.padding(2.dp))
        }

        /*me.Default.forEach {\
            defaultCard(context,it)
        }*/

    }
}


@Composable
fun defaultCard(me: UserWithAbilities, context: Context, index:Int, screen: TypedDestination<*>, navigator: DestinationsNavigator){
    Row(modifier= Modifier.clickable{
        me.user.defaultScreen=index
        navigator.navigate(screen.route)
    }){
        Column{
            Text(""+screen.route)
            }


    }
}
@Composable
fun DefaultLazyColumn(
    navigator:DestinationsNavigator, context:Context,
    viewModel: UserViewModel, Screens: List<TypedDestination<*>>, modifier: Modifier){
    var selectedIndex by remember { mutableStateOf(0) }
    val onItemClick = { index: Int -> selectedIndex = index}
/*
    Text(
        "placeholder1"//text = "Index $index",
    )*/
    val state = rememberLazyListState()
    androidx.compose.foundation.lazy.LazyColumn(state = state,
        modifier = modifier.fillMaxSize(),
    ) {
        itemsIndexed(Screens) {ix, screen ->

            DefaultDestinationViewShort(
                navigator = navigator,
                screen =screen,
                context = context,
                viewModel = viewModel,
                me =viewModel.getMeWithAbilities(),
                index = ix,
                selected = selectedIndex == ix,
                onClick = onItemClick
            )



        }




        //replace count with default.objectives.size
        //val mObserver = Observer<List<QuestWithObjectives>> { qwe->

    }}
/*Text(
    "padding"//text = "Index $index",
)
}*/

@Composable
fun DefaultDestinationViewShort(
    navigator: DestinationsNavigator,
    screen: TypedDestination<*>,
    context: Context,
    viewModel: UserViewModel,
    me: UserWithAbilities,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    //Text("DEBUG")
    Box(modifier = Modifier
        .clickable {
            me.user.defaultScreen=index
            onClick.invoke(index)

        }
        .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)

        //.fillMaxWidth()
        .padding(12.dp)) {
        /*Text(
            text = "Index $index",
        )*/

        defaultCard(
            me =viewModel.getMeWithAbilities(),
            screen =screen,
            index =index, context = context, navigator = navigator)

    }
    //Text("DEBUG2")
}



