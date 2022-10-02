package com.instance.dataxbranch.ui


import android.content.Context

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
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.instance.dataxbranch.ui.NavGraphs
import com.instance.dataxbranch.data.local.UserWithAbilities

import com.instance.dataxbranch.ui.components.TermsPopupAlertDialog
import com.instance.dataxbranch.ui.destinations.*


//import com.instance.dataxbranch.ui.destinations.Destination

import com.instance.dataxbranch.ui.viewModels.UserViewModel
import com.instance.dataxbranch.utils.constants.DEFAULT_UNAME
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPagerApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HubScreen(viewModel: UserViewModel = hiltViewModel(),
              navigator: DestinationsNavigator) {


    val context = LocalContext.current
    Scaffold(



    ) { padding ->
        if (viewModel.singleConditionsDialog.value) {
            if (viewModel.termsDialogState.value) {
                TermsPopupAlertDialog(viewModel,navigator)
            }
        }
        //startActivity(context, StartupActivity.createIntent(context),null)
        var me = viewModel.getMeWithAbilities()
        if (me.user.fsid=="-1")
        if (me.user.uname ==DEFAULT_UNAME && !me.user.initflag){viewModel.refresh(true,true)
            //Log.d(TAG,"REPEATER")
            /*navigator.navigate(
                HubScreenDestination)*/}
        me = viewModel.getMeWithAbilities()

       // val which = me.user.defaultScreen
        val dests = NavGraphs.root.destinations
        if (me.user.terms_status ==""){//if user has not accepted terms
            viewModel.termsDialogState.value=true
        }else {
            /*if (which >= 0) {
                navigator.navigate(
                    dests[which].route
                )
            }*/
        }
        Column{Text(me.user.uname)
            //Text("GO TO USER SCREEN -> save")
            DefaultLazyColumn(navigator,context,viewModel, screens = dests, modifier = Modifier.padding(2.dp))
        }

        /*me.Default.forEach {\
            defaultCard(context,it)
        }*/

    }
}


@Composable
fun defaultCard(me: UserWithAbilities, index:Int, screen: TypedDestination<*>, navigator: DestinationsNavigator){
    Row(modifier= Modifier.clickable{
        me.user.defaultScreen =index
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
    viewModel: UserViewModel,screens: List<TypedDestination<*>>, modifier: Modifier){
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
        itemsIndexed(screens) {ix, screen ->

            DefaultDestinationViewShort(
                navigator = navigator,
                screen =screen,

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
    viewModel: UserViewModel,
    me: UserWithAbilities,
    index: Int,
    selected: Boolean,
    onClick: (Int) -> Unit
) {
    //Text("DEBUG")
    Box(modifier = Modifier
        .clickable {
            me.user.defaultScreen =index
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
            index =index,  navigator = navigator)

    }
    //Text("DEBUG2")
}



