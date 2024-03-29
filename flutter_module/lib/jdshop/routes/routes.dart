import 'package:flutter/material.dart';
import 'package:flutter_module/jdshop/pages/tabs.dart';
import 'package:flutter_module/widget/pages/channel_page.dart';
import 'package:flutter_module/widget/pages/form_page.dart';
import 'package:flutter_module/widget/pages/login_page.dart';
import 'package:flutter_module/widget/pages/product_info_page.dart';
import 'package:flutter_module/widget/pages/product_page.dart';
import 'package:flutter_module/widget/pages/register_first_page.dart';
import 'package:flutter_module/widget/pages/register_second_page.dart';
import 'package:flutter_module/widget/pages/register_thrid_page.dart';
import 'package:flutter_module/widget/pages/search_page.dart';
import 'package:flutter_module/widget/statefulwidget/default_tabcontroller_appbar_tabbar_tabbarview.dart';
import 'package:flutter_module/widget/statefulwidget/drawer_drawer_header.dart';
import 'package:flutter_module/widget/statefulwidget/drawer_useraccouts_drawer_header.dart';
import 'package:flutter_module/widget/statefulwidget/scaffold_bottom_navigation_bar_1.dart';
import 'package:flutter_module/widget/statefulwidget/tabcontroller_appbar_tabbar_tabbarview.dart';

///配置路由,定义Map类型的routes,Key为String类型，Value为Function类型
final Map<String ,Function>  routes={
  '/':(context,{arguments})=>ChannelPage(arguments:arguments),
  '/tabs':(context)=>Tabs(),
  '/index_demo':(context)=>ScaffoldBottomNavigationBar1(),
  '/form':(context)=>FormPage(),
  '/search':(context,{dynamic arguments})=>SearchPage(arguments:arguments),//dynamic可以去掉，默认是这种类型
  '/product':(context)=>ProductPage(),
  '/product_info':(context,{arguments})=>ProductInfoPage(arguments:arguments),
  '/login':(context)=>LoginPage(),
  '/register_first':(context)=>RegisterFirstPage(),
  '/register_second':(context)=>RegisterSecondPage(),
  '/register_thrid':(context)=>RegisterThridPage(),
  '/default_tabcontrol_appbar_tabbar_tabbarview':(context)=>DefaultTabControllerAppBarTabBarTabBarView(),
  '/tabcontrol_appbar_tabbar_tabbarview':(context)=>TabControllerAppBarTabBarTabBarView(),
  '/drawer_drawer_header':(context)=>DrawerDrawerHeader(),
  '/drawer_useraccounts_drawer_header':(context)=>DrawerUserAccountsDrawerHeader()

};

///固定写法
dynamic onGenerateRoute=(RouteSettings settings) {
  ///String? 表示name为可空类型
  final String? name = settings.name;
  ///Function? 表示pageContentBuilder为可空类型
  final Function? pageContentBuilder = routes[name];
  if (pageContentBuilder != null) {
    if (settings.arguments != null) {
      final Route route = MaterialPageRoute(
          builder: (context) =>
              pageContentBuilder(context, arguments: settings.arguments));
      return route;
    }else{
      final Route route = MaterialPageRoute(
          builder: (context) =>
              pageContentBuilder(context));
      return route;
    }
  }
};

