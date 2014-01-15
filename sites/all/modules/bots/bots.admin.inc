<?php
/**
* @file
* Administration page callbacks for the bots module.
*/
/**
* Form builder. Configure annotations.
*
* @ingroup forms
* @see system_settings_form().
*/
function bots_admin_settings() {
	$form['bots_file_path'] = array(
		'#type' => 'textfield',
		'#title' => t('Bots path'),
		//'#options' => $options,
		'#default_value' => variable_get('bots_file_path', array('/com/boryi/bots/jobs/')),
		'#description' => t('The path where bots are deployed.'),
		'#size'=> 64,
	);
	$form['bots_data_path'] = array(
		'#type' => 'textfield',
		'#title' => t('Bots data path'),
		//'#options' => $options,
		'#default_value' => variable_get('bots_data_path', array('/var/www/data/')),
		'#description' => t('The path where bots data files are exported.'),
		'#size'=> 64,
	);
	$form['#submit'][] = 'bots_admin_settings_submit';
	return system_settings_form($form);
}

/**
* Validate bots settings submission.
*/
function bots_admin_settings_validate($form, &$form_state) {
	$path = $form_state['values']['bots_file_path'];
	if (!is_dir($path)) {
		form_set_error('bots_file_path', t('Please enter an legal path.'));
	}

	$datapath = $form_state['values']['bots_data_path'];
	if (!is_dir($datapath)) {
		form_set_error('bots_data_path', t('Please enter an legal path.'));
	}
	// to force the path ends with a '/'
	$form_state['values']['bots_file_path'] = 
		rtrim($path, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
	$form_state['values']['bots_data_path'] = 
		rtrim($datapath, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
}

/**
* Process bots settings submission.
*/
function bots_admin_settings_submit($form, $form_state) {
  // 
}