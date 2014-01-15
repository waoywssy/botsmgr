<?php

function get_view(){

    drupal_add_library('system','ui.widget');
    drupal_add_library('system','ui.button');
    drupal_add_library('system','ui.mouse');
    drupal_add_library('system','ui.draggable');
    drupal_add_library('system','ui.position');
    drupal_add_library('system','ui.dialog');
    drupal_add_library('system','ui.tabs');
    drupal_add_library('system','ui.datepicker');

    drupal_add_css(drupal_get_path('module', 'jquery_update') . '/replace/ui/themes/base/jquery.ui.all.css');
    drupal_add_css(drupal_get_path('module', 'jquery_update') . '/replace/ui/themes/base/ui.datepicker.css');

    drupal_add_css(drupal_get_path('module', 'bots') . '/files/jqGrid/css/ui.jqgrid.css');
    drupal_add_css(drupal_get_path('module', 'bots') . '/css/bots.css');
    drupal_add_css(drupal_get_path('module', 'bots') . '/css/ajaxfileupload.css');
    drupal_add_css(drupal_get_path('module', 'bots') . '/css/uploadify.css');

    drupal_add_js(drupal_get_path('module', 'bots') . '/files/jqGrid/js/jqGrid/i18n/grid.locale-en.js');
    drupal_add_js(drupal_get_path('module', 'bots') . '/files/jqGrid/js/jqGrid/jquery.jqGrid.min.js');
    drupal_add_js(drupal_get_path('module', 'bots') . '/js/jquery.validate.js');

    drupal_add_js('var botsPath = \'' . variable_get('bots_file_path', '/') . '\';', 'inline');
	drupal_add_js(drupal_get_path('module', 'bots') . '/js/viewbots.js');

	global $user;
	if (user_access('edit bot info', $user)) {
		drupal_add_js(drupal_get_path('module', 'bots') . '/js/gridpagers.js');
	}
	drupal_add_js(drupal_get_path('module', 'bots') . '/js/jquery.contextmenu.js');
	drupal_add_js(drupal_get_path('module', 'bots') . '/js/ajaxfileupload.js');
    drupal_add_js(drupal_get_path('module', 'bots') . '/js/jquery.uploadify.js');

	$tpl_dir = dirname(dirname(__FILE__)) . '/tpl/';

    $html = file_get_contents($tpl_dir . 'tabs_grids.tpl');
	return $html;
}