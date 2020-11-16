package com.evig.sapphire.test;

public interface Locators {

    //Navigation
    public static final String ShopPinkNowButton = "//*[contains(@resource-id,'anonymousShopBtn')]";
    public static final String DASHBOARD_TITLE_BAR = "//*[contains(@resource-id,'dashboardTitleBar') or @name='Pink Nation' or @text='Pink Nation' or @label='Pink Nation']";
    public static final String DownArrowButton = "//*[contains(@resource-id,'navbarArrow')] | //*[@name='Pink Nation']/following::*[1]";
    public static final String PinkViewerInNav = "//*[contains(@resource-id,'pinkViewerNavButton') or @name='pink viewer']";
    public static final String PinkViewerTitle = "//*[contains(@resource-id,'module_title_view')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]";
    public static final String SHOP_IN_NAV = "//*[contains(@resource-id,'shopNavButton') or @name='shop']";
    public static final String BraLoveInNav = "//*[contains(@resource-id,'braLoveNavButton') or @name='bra love']";
    public static final String ME_NAV = "//*[contains(@content-desc,'Me, button') or @name='me']";
    public static final String HOME_NAV = "//*[contains(@resource-id,'homeNavButton') or @name='home']";
    public static final String MeNavBadgePage_title = "//*[contains(@resource-id,'my_badge_sub_head_text_view')]";
    public static final String UserName_FirstName = "//*[contains(@resource-id,'nameSubHeaTextView')]";
    public static final String BraLoveCloseButton = "//*[contains(@resource-id,'close') or @name='bra love']";
    public static final String ShopCloseButton = "//*[contains(@resource-id,'close') ]";

    //Webview
    public static final String PNShopWebView = "//*[contains(@content-desc,'Secret')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]";
    public static final String BraLoveWebView = "//*[contains(@content-desc,'BRAS')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[2]/XCUIElementTypeOther[1]";
    public static final String GoogleChromeIcon = "(//*[contains(@resource-id,'icon')])[1]";
    public static final String ForgotPasswordScreenTextWebView = "//*[contains(@content-desc,'Forgot Your PASSWORD')]";
    public static final String ForgotPasswordFormWebView = "//*[contains(@resource-id,'account-form')] | //*[@name = 'WebView']";
    public static final String ForgotPasswordSubmitButtonWebView = "//*[contains(@content-desc,'SUBMIT')]";
    public static final String ResetPasswordScreenTextWebView = "//*[contains(@content-desc,'PASSWORD RESET EMAIL')]";
    public static final String ResetPasswordConfirmationTextWebView = "//*[contains(@content-desc,'email has been sent to you that contains a link to reset')]";

    //Anonymous User Landing
    public static final String CarousalOnLandingPage = "//*[contains(@resource-id,'pagerImage')] | //*[@name='item0']";
    public static final String CTAButtonPanel = "//*[contains(@resource-id,'buttonPanel')] | //*[contains(@name,'JOIN PINK NATION')]/../..";
    public static final String JoinPinkNation = "//*[contains(@resource-id,'joinPinkBtn')] | //*[contains(@name,'JOIN PINK NATION')]/..";
    public static final String MEMBER_SIGN_IN = "//*[contains(@resource-id,'signInBtn')] | //*[contains(@name,'MEMBER SIGN IN')]/..";
    public static final String SHOP_PINK_NOW = "//*[contains(@resource-id,'anonymousShopBtn')] | //*[contains(@name,'SHOP PINK NOW')]/..";
    public static final String PaginationDots = "//*[contains(@resource-id,'dotOne')]/../child::* | //*[contains(@name,'Exclusive perks for the ultimate')]/../../../following-sibling::*[1]";
    public static final String JOIN_PINK_NATION_BTN = "//*[contains(@resource-id,'joinPinkBtn')] | //*[contains(@name,'JOIN PINK NATION')]";
    public static final String MemberSignIn_Btn = "//*[contains(@resource-id,'signInBtn')] | //*[contains(@name,'MEMBER SIGN IN')]";
    public static final String ShopPinkNow_Btn = "//*[contains(@resource-id,'anonymousShopBtn')] | //*[contains(@name,'SHOP PINK NOW')]";


    //Generic Locators
    public static final String return_Button = "//*[@name='Return']";
    public static final String delete_Button = "//*[@name='Delete']";
    public static final String notificationDontAllow = "//*[@name='Don’t Allow']";
    public static final String notificationAlertPopup = "//*[contains(@name,'These can be configured in Settings.')]";


    //Member Sign In - Login , Forgot PASSWORD
    public static final String EMAIL = "Email";
    public static final String CONFIRM_EMAIL = "Confirm EMAIL";
    public static final String PASSWORD = "Password";
    public static final String SIGN_IN_BUTTON = "//*[contains(@label,'sign in to pink')] | //*[contains(@resource-id,'sign_in_button')]/child::*/child::*";
    public static final String SIGN_IN_NAV_BAR_LABEL = "//*[@content-desc='MEMBER SIGN IN'] | //*[@label='member sign in' or @name='member sign in']";
    public static final String SIGN_IN_BACK_BUTTON = "//*[@content-desc='Back' or @label='Back']";
    public static final String SignInPinkHeartIcon = "//*[contains(@resource-id,'pinkHeartIcon')]";
    public static final String SignInEmailAddressFieldLabel = "//*[contains(@text,'EMAIL')] | //*[contains(@label,'EMAIL')]/following-sibling::*";
    public static final String SignInPasswordFieldLabel = "//*[contains(@text,'PASSWORD')] | //*[contains(@label,'PASSWORD')]/following-sibling::*";
    public static final String SignIn_UseFingerPrint_And_TouchID_IoS = "//*[@content-desc='Use Fingerprint' or @label='Use Touch ID']";
    public static final String SignInToPinkNationButton = "//*[contains(@content-desc,'Pink Nation')]";
    public static final String SignInForgotPassword_Button = "//*[contains(@resource-id,'forgot_password_text_view') or @label='Forgot your password?']";
    public static final String SignInFaceBookUser_Button = "//*[contains(@resource-id,'facebook_header_text_view') or @label='FACEBOOK USER?']";
    public static final String SignInFacebookUserBody_txt = "//*[contains(@content-desc,'We are no longer using Facebook') or contains(@label,'We are no longer using Facebook')]";
    public static final String SignInNotAMember_Button = "//*[contains(@text,'NOT A MEMBER') or contains(@label,'Not a Memeber')]";
    public static final String InvalidCredential_ErrorMessage = "//*[@text='Oops!']/../../following::*[1]//child::*[contains(@resource-id,'message')] | //*[contains(@label,'We do not') or contains(@label,'we do not')]/preceding::*[1]/following::*[1]";
    public static final String InvalidEmailAddressFormat = "//XCUIElementTypeStaticText[contains(@name,'Invalid EMAIL')] | (//*[contains(@resource-id,'hintErrorView')])[1]";
    public static final String BlankPasswordErrorMessage = "//XCUIElementTypeSecureTextField/following::*[1] | //*[contains(@text,'Please enter a')]";
    public static final String BlankEmailErrorMessage = "//*[contains(@text,'enter an EMAIL Address')] | //XCUIElementTypeStaticText[contains(@name,'Please enter an')]";
    public static final String ForgotPasswordResetButton = "//*[contains(@resource-id,'reset_btn') or @name='RESET YOUR PASSWORD']";
    public static final String OK_BUTTON_LOCATOR = "//*[contains(@resource-id,'button1') or @label='OK']";
    public static final String LockedAccountErrorMessage = "//*[@text='Oops!']/../../following::*[1]//child::*[contains(@resource-id,'message')] | //*[contains(@label,'Your account has')]/preceding::*[1]/following::*[1]";
    public static final String ForgotPasswordButton = "//*[contains(@resource-id,'orgot_password_text_view')] | //*[@label='Forgot your password?']";
    public static final String ResetPasswordScreenText = "//*[@content-desc='UH-OH!'] | //*[@label='UH-OH!']";
    public static final String ResetPasswordVerificationText = "//*[contains(@content-desc,'Check your email for instructions')] | (//*[contains(@label,'Check your email for instructions')])";
    public static final String InvalidEmailAddressFormat_JoinPinkNation1OF2 = "//XCUIElementTypeTextField[contains(@name,'Invalid EMAIL')] | (//*[contains(@resource-id,'hintErrorView')])[1]";
    public static final String BlankErrorMessage_JoinPinkNation1OF2 = "//*[contains(@text,'enter an EMAIL Address')] | //XCUIElementTypeTextField[contains(@name,'Please enter')]";

    //Member Sign In - Join Pink Nation
    public static final String JoinPinkNation_Headertitle = "//*[@content-desc='Join Pink Nation' or @label='join pink nation']";
    public static final String JOIN_PINK_NATION_NAV_BAR_LABEL = "//*[@content-desc='Step 1 of 2' or @label='1 of 2' or @name='1 of 2']";
    public static final String JoinPinkNation_HeaderBody = "//*[contains(@content-desc,'get in on exclusive offers') or contains(@label,'get in on exclusive offers')]";
    public static final String JoinPinkNation_EmailAddressInput = "//*[contains(@text,'EMAIL')] | //XCUIElementTypeTextField";
    public static final String JoinPinkNation_EmailOptText = "//*[contains(@text,'I want PINK Nation')] | //XCUIElementTypeButton[@name='yes, i want pink nation emails']";
    public static final String JOIN_PINK_NATION_CONTINUE_BUTTON = "//*[contains(@text,'CONTINUE') or @name='continue']";
    public static final String JOIN_PINK_NATION_2_OF_2_NAV_BAR_LABEL = "//*[@content-desc='page 2 of 2' or @label='2 of 2' or @name='2 of 2']";
    public static final String JoinPinkNation_2OF2_Headertitle = "//*[@content-desc='almost done!' or @label='almost done!']";
    public static final String JoinPinkNation_2OF2_HeaderBody = "//*[contains(@content-desc,'get access to both PINK Nation') or contains(@label,'get access to both pink nation')]";
    public static final String JoinPinkNation_2OF2_PasswordFormTitle = "//*[contains(@content-desc,'Requirements, heading') or contains(@label,'requirements')]";
    public static final String JoinPinkNation_2OF2_CreatePasswordInput = "//*[contains(@text,'Create PASSWORD')] | //*[@name='Show']/preceding-sibling::*[1]";
    public static final String JoinPinkNation_2OF2_MailingAddressInput = "//*[contains(@text,'Mailing Address') or contains(@label,'mailing ADDRESS')]";
    public static final String JoinPinkNation_2OF2_FirstNameInput = "//*[contains(@text,'First Name')] | (//*[@label='First Name'])[2]";
    public static final String JoinPinkNation_2OF2_LastNameInput = "//*[contains(@text,'Last Name')] | (//*[contains(@label,'Last Name')])[3]";
    public static final String JoinPinkNation_2OF2_AddressInput = "//*[contains(@text,'Address')] | (//*[contains(@label,'Address')])[2]";
    public static final String JoinPinkNation_2OF2_CityInput = "//*[contains(@text,'City')] | (//*[contains(@label,'City')])[2]";
    public static final String JoinPinkNation_2OF2_StateSelector = "//*[contains(@text,'State')] | (//*[contains(@label,'State')])[2]";
    public static final String JoinPinkNation_2OF2_ZipCodeInput = "//*[contains(@text,'Zip Code')] | (//*[contains(@label,'Zip Code')])[2]";
    public static final String JoinPinkNation_2OF2_FavoriteStoreFormTitle = "//*[contains(@text,'Your favorite PINK store:') or contains(@label,'your favorite pink store')]";
    public static final String JoinPinkNation_2OF2_StoreSelector = "//*[contains(@text,'Please select your store')] | (//*[contains(@label,'Please select your store')])[2]";
    public static final String BIRTHDAY_TV_LOCATOR = "//*[contains(@text,'Birthday')] | (//*[contains(@label,'Birthday')])[2]";
    public static final String COLLEGE_TV_LOCATOR = "//*[contains(@text,'College')] | (//*[contains(@label,'College')])[2]";
    public static final String GRAD_YEAR_TV_LOCATOR = "//*[contains(@text,'Grad Year')] | (//*[contains(@label,'Grad Year')])[2]";
    public static final String JoinPinkNation_2OF2_OptionalMobileNumberInput = "//*[contains(@text,'Mobile')] | (//*[contains(@label,'Mobile')])[2]";
    public static final String JoinPinkNation_2OF2_TextMessageOptText = "//*[contains(@text,'I want PINK Nation texts')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[2]";
    public static final String JoinPinkNation_2OF2_DisclaimerText = "//*[contains(@text,'By checking this box and clicking submit') or contains(@label,'By checking this box and clicking submit')]";
    public static final String JoinPinkNation_2OF2_UseTouchIDLabel = "//*[contains(@text,'Use Fingerprint') or contains(@label,'Use Touch ID')]";
    public static final String SUBMIT_BUTTON_LOCATOR = "//*[contains(@text,'SUBMIT')] | (//*[contains(@label,'submit')])[2]";
    public static final String JoinPinkNation_VSHolderScreen_ClearButton = "(//*[contains(@resource-id,'clearBtn')])[1] | //*[@name='clear']";
    public static final String JoinPinkNation_VSHolderScreen_NextButton = "(//*[contains(@resource-id,'sign_in_vs_pink_button')])[1] | //*[@name='next step']";
    public static final String JoinPinkNation_2OF2_StateSelector_Spinner = "//*[contains(@resource-id,'spinner')]";
    public static final String JoinPinkNation_2OF2_CollegeSuggestionBackButton = "//*[@content-desc='Back']";
    public static final String SUGGESTIONS_TV_LOCATOR = "//*[@text='SUGGESTIONS:']";
    public static final String FIRST_COLLEGE_ITEM_LOCATOR = "//*[contains(@resource-id,'collegeResults')]/child::*[1]";
    public static final String JoinPinkNation_VSAccountForgotPasswordLink = "//*[contains(@resource-id,'forgot_password_text_view')] | //*[@name='Forgot your VS password?']";
    public static final String JoinPinkNation_VSAccountTitleTextView = "//*[contains(@resource-id,'vs_account_title_text_view')] | //*[@name='HEY VS ACCOUNT HOLDER!']";
    public static final String JoinPinkNation_VSAccountBodyTextView = "//*[contains(@resource-id,'vs_accout_body_text_view')] | //*[contains(@label,'Use your Victoria’s Secret login')]";
    public static final String JoinPinkNation_ForgotPasswordView = "//*[contains(@resource-id,'resolver_list')]";
    public static final String JoinPinkNation_ExistingAccountErrorMessage = "//*[@text='Oops!']/../../following::*[1]//child::*[contains(@resource-id,'message')] | //*[contains(@label,'email ADDRESS provided')]/preceding::*[1]/following::*[1]";


    //Elements visible names used to fill JoinPinkNation Page 2 OF 2
    public static final String CREATE_PASSWORD = "Create PASSWORD";
    public static final String FIRST_NAME = "First Name";
    public static final String LAST_NAME = "Last Name";
    public static final String ADDRESS = "Address";
    public static final String CITY = "City";
    public static final String ZIP_CODE = "Zip Code";
    public static final String BIRTHDAY = "Birthday";
    public static final String COLLEGE = "College";
    public static final String GRAD_YEAR = "Grad Year";
    public static final String MOBILE = "Mobile";
    public static final String ALABAMA = "Alabama";

    // Me and Badges
    public static final String BADGE_IMAGE = "//*[contains(@resource-id,'badge_image')]";
    public static final String PROUD_MEMBER_BADGE_COPY = "//*[contains(@text,'unlocked your first badge')]";
    public static final String badgeWallpaperButton = "//*[contains(@resource-id,'wallpaper_btn')]";
    public static final String badgeBragAboutItButton = "//*[contains(@resource-id,'brag_btn')]";
    public static final String seeAllBadgesButton = "//*[contains(@resource-id,'continue_btn')]";
    public static final String mePageLogoutButton = "//*[contains(@resource-id,'lof_of_pink_nation_button')]";
    public static final String confirmlogoutPopupButton = "//*[contains(@text,'LOG OUT')]";
    public static final String lockedHookedBadge = "//*[contains(@text,'HOOKED')]/preceding-sibling::*[1]";
    public static final String badgeHintPopUp = "//*[contains(@resource-id,'parentPanel')]";
    public static final String hookedBadgeHintAlertTitle = "//*[contains(@text,'Hooked')]";
    public static final String hookedBadgeHintDescription = "//*[contains(@text,'Hint: Get some Bra Love!')]";
    public static final String badgeHintOKButton = "//*[contains(@resource-id,'button1')]";
    public static final String lockedShopBadge = "//*[contains(@text,'SHOPAHOLIC')]/preceding-sibling::*[1]";
    public static final String shopBadgeHintAlertTitle = "//*[contains(@text,'Shopaholic')]";
    public static final String shopBadgeHintDescription = "//*[contains(@text,'Hint: Get shopping in the app, stat!')]";
    public static final String hookedBadgeCopy = "//*[contains(@text,'showing us some Bra Love')]";
    public static final String shopBadgeCopy = "//*[contains(@text,'Cause you got your shop on')]";


    //Navigation
    String DASHBOARD_TITLE_LOCATOR = "//*[contains(@resource-id,'dashboardTitleBar') or @name='Pink Nation' or @text='Pink Nation' or @label='Pink Nation']";
    String DOWN_ARROW_LOCATOR = "//*[contains(@resource-id,'navbarArrow')] | //*[@name='Pink Nation']/following::*[1]";
    String PINK_VIEWER_IN_NAV_LOCATOR = "//*[contains(@resource-id,'pinkViewerNavButton') or @name='pink viewer']";
    String PINK_VIEWER_TITLE_LOCATOR = "//*[contains(@resource-id,'module_title_view')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]";
    String SHOP_NAV_ITEM_LOCATOR = "//*[contains(@resource-id,'shopNavButton') or @name='shop']";
    String BRA_LOVE_NAV_ITEM_LOCATOR = "//*[contains(@resource-id,'braLoveNavButton') or @name='bra love']";
    String ME_NAV_ITEM_LOCATOR = "//*[contains(@content-desc,'Me, button') or @name='me']";
    String HOME_NAV_ITEM_LOCATOR = "//*[contains(@resource-id,'homeNavButton') or @name='home']";
    String BRA_LOVE_LOCATOR = "//*[contains(@content-desc,'BRAS')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[2]/XCUIElementTypeOther[1]";

    //Anonymous User Landing
    String MEMBER_SIGN_IN_BTN_LOCATOR = "//*[contains(@resource-id,'signInBtn')] | //*[contains(@name,'MEMBER SIGN IN')]/..";
    String SHOP_PINK_NOW_BTN_LOCATOR = "//*[contains(@resource-id,'anonymousShopBtn')] | //*[contains(@name,'SHOP PINK NOW')]/..";
    String JOIN_PINK_NATION_BTN_LOCATOR = "//*[contains(@resource-id,'joinPinkBtn')] | //*[contains(@name,'JOIN PINK NATION')]";


    //Member Sign In - Login , Forgot PASSWORD_LABEL
    String SIGN_IN_BUTTON_LOCATOR = "//*[contains(@label,'sign in to pink')] | //*[contains(@resource-id,'sign_in_button')]";
    String SIGN_IN_NAV_BAR_LABEL_LOCATOR = "//*[@content-desc='MEMBER SIGN IN'] | //*[@label='member sign in' or @name='member sign in']";
    String SIGN_IN_BACK_BUTTON_LOCATOR = "//*[@content-desc='Back' or @label='Back']";
    String SIGN_IN_PINK_HEART_ICON_LOCATOR = "//*[contains(@resource-id,'pinkHeartIcon')]";
    String SIGN_IN_EMAIL_ADDRESS_FIELD_LABEL_LOCATOR = "//*[contains(@text,'Email')] | //*[contains(@label,'Email')]/following-sibling::*";
    String SIGN_IN_PASSWORD_FIELD_LABEL_LOCATOR = "//*[contains(@text,'Password')] | //*[contains(@label,'Password')]/following-sibling::*";
    String SIGN_IN_USE_FINGER_PRINT_AND_TOUCH_ID_IOS_LOCATOR = "//*[@content-desc='Use Fingerprint' or @label='Use Touch ID']";
    String SIGN_IN_FORGOT_PASSWORD_BUTTON_LOCATOR = "//*[contains(@resource-id,'forgot_password_text_view') or @label='Forgot your password?']";
    String SIGN_IN_FACE_BOOK_USER_BUTTON_LOCATOR = "//*[contains(@resource-id,'facebook_header_text_view') or @label='FACEBOOK USER?']";
    String SIGN_IN_FACEBOOK_USER_BODY_TXT_LOCATOR = "//*[contains(@content-desc,'We are no longer using Facebook') or contains(@label,'We are no longer using Facebook')]";
    String SIGN_IN_NOT_A_MEMBER_BUTTON_LOCATOR = "//*[contains(@text,'NOT A MEMBER') or contains(@label,'Not a Memeber')]";
    String INVALID_CREDENTIAL_ERROR_LOCATOR = "//*[@text='Oops!']/../../following::*[1]//child::*[contains(@resource-id,'message')] | //*[contains(@label,'We do not') or contains(@label,'we do not')]/preceding::*[1]/following::*[1]";
    String BLANK_PWD_ERROR_LOCATOR = "//XCUIElementTypeSecureTextField/following::*[1] | //*[contains(@text,'Please enter a')]";
    String RESET_PASSWORD_LOCATOR = "//*[contains(@resource-id,'reset_btn') or @name='RESET YOUR PASSWORD_LABEL']";
    String LOCKED_ACCOUNT_ERROR_MESSAGE_LOCATOR = "//*[@text='Oops!']/../../following::*[1]//child::*[contains(@resource-id,'message')] | //*[contains(@label,'Your account has')]/preceding::*[1]/following::*[1]";
    String FORGET_PASSWORD_LOCATOR = "//*[contains(@resource-id,'orgot_password_text_view')] | //*[@label='Forgot your password?']";
    String RESET_PASSWORD_SUCESS_LOCATOR = "//*[contains(@content-desc,'Check your email for instructions')] | (//*[contains(@label,'Check your email for instructions')])";

    //Member Sign In - Join Pink Nation
    String JOIN_PINK_NATION_NAV_BAR_LABEL_LOCATOR = "//*[@content-desc='Step 1 of 2' or @label='1 of 2' or @name='1 of 2']";
    String CONTINUE_BUTTON_LOCATOR = "//*[contains(@text,'CONTINUE') or @name='continue']";
    String JOIN_PINK_NATION_2_OF_2_NAV_BAR_LABEL_LOCATOR = "//*[@content-desc='page 2 of 2' or @label='2 of 2' or @name='2 of 2']";
    String JOIN_PINK_NATION_2_OF_2_HEADERTITLE_LOCATOR = "//*[@content-desc='almost done!' or @label='almost done!']";
    String JOIN_PINK_NATION_2_OF_2_HEADER_BODY_LOCATOR = "//*[contains(@content-desc,'get access to both PINK Nation') or contains(@label,'get access to both pink nation')]";
    String JOIN_PINK_NATION_2_OF_2_PASSWORD_FORM_TITLE_LOCATOR = "//*[contains(@content-desc,'Requirements, heading') or contains(@label,'requirements')]";
    String JOIN_PINK_NATION_2_OF_2_CREATE_PASSWORD_INPUT_LOCATOR = "//*[contains(@text,'Create Password')] | //*[@name='Show']/preceding-sibling::*[1]";
    String JOIN_PINK_NATION_2_OF_2_MAILING_ADDRESS_INPUT_LOCATOR = "//*[contains(@text,'Mailing Address') or contains(@label,'mailing ADDRESS')]";
    String JOIN_PINK_NATION_2_OF_2_FIRST_NAME_INPUT_LOCATOR = "//*[contains(@text,'First Name')] | (//*[@label='First Name'])[2]";
    String JOIN_PINK_NATION_2_OF_2_LAST_NAME_INPUT_LOCATOR = "//*[contains(@text,'Last Name')] | (//*[contains(@label,'Last Name')])[3]";
    String JOIN_PINK_NATION_2_OF_2_ADDRESS_INPUT_LOCATOR = "//*[contains(@text,'Address')] | (//*[contains(@label,'Address')])[2]";
    String JOIN_PINK_NATION_2_OF_2_CITY_INPUT_LOCATOR = "//*[contains(@text,'City')] | (//*[contains(@label,'City')])[2]";
    String JOIN_PINK_NATION_2_OF_2_STATE_SELECTOR_LOCATOR = "//*[contains(@text,'State')] | (//*[contains(@label,'State')])[2]";
    String JOIN_PINK_NATION_2_OF_2_ZIP_CODE_INPUT_LOCATOR = "//*[contains(@text,'Zip Code')] | (//*[contains(@label,'Zip Code')])[2]";
    String JOIN_PINK_NATION_2_OF_2_FAVORITE_STORE_FORM_TITLE_LOCATOR = "//*[contains(@text,'Your favorite PINK store:') or contains(@label,'your favorite pink store')]";
    String JOIN_PINK_NATION_2_OF_2_STORE_SELECTOR_LOCATOR = "//*[contains(@text,'Please select your store')] | (//*[contains(@label,'Please select your store')])[2]";
    String JOIN_PINK_NATION_2_OF_2_OPTIONAL_MOBILE_NUMBER_INPUT_LOCATOR = "//*[contains(@text,'Mobile')] | (//*[contains(@label,'Mobile')])[2]";
    String JOIN_PINK_NATION_2_OF_2_TEXT_MESSAGE_OPT_TEXT_LOCATOR = "//*[contains(@text,'I want PINK Nation texts')] | //XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[2]";
    String JOIN_PINK_NATION_2_OF_2_DISCLAIMER_TEXT_LOCATOR = "//*[contains(@text,'By checking this box and clicking submit') or contains(@label,'By checking this box and clicking submit')]";
    String JOIN_PINK_NATION_2_OF_2_USE_TOUCH_ID_LABEL_LOCATOR = "//*[contains(@text,'Use Fingerprint') or contains(@label,'Use Touch ID')]";
    String JOIN_VS_NEXT_BUTTON_LOCATOR = "(//*[contains(@resource-id,'sign_in_vs_pink_button')])[1] | //*[@name='next step']";
    String JOIN_PN_VS_FORGET_ACCOUNT_LOCATOR = "//*[contains(@resource-id,'forgot_password_text_view')] | //*[@name='Forgot your VS password?']";
    String JOIN_PN_VS_ACCOUNT_LOCATOR = "//*[contains(@resource-id,'vs_account_title_text_view')] | //*[@name='HEY VS ACCOUNT HOLDER!']";
    String INVALID_EMAIL_ADDRESS_FORMAT_LOCATOR = "//XCUIElementTypeStaticText[contains(@name,'Invalid EMAIL')] | (//*[contains(@resource-id,'hintErrorView')])[1]";

    // JOIN 1 of 2
    String JOIN_PINK_NATION_HEADERTITLE_LOCATOR = "//*[@content-desc='Join Pink Nation' or @label='join pink nation']";
    String JOIN_PINK_NATION_HEADER_BODY_LOCATOR = "//*[contains(@content-desc,'get in on exclusive offers') or contains(@label,'get in on exclusive offers')]";
    String JOIN_PINK_NATION_EMAIL_ADDRESS_INPUT_LOCATOR = "//*[contains(@text,'Email')] | //XCUIElementTypeTextField";
    String JOIN_PINK_NATION_EMAIL_OPT_TEXT_LOCATOR = "//*[contains(@text,'I want PINK Nation')] | //XCUIElementTypeButton[@name='yes, i want pink nation emails']";

    //Elements visible names used to fill JoinPinkNation Page 2 OF 2
    String CREATE_PASSWORD_LABEL = "Create Password";
    String FIRST_NAME_LABEL = "First Name";
    String LAST_NAME_LABEL = "Last Name";
    String ADDRESS_LABEL = "Address";
    String CITY_LABEL = "City";
    String ZIP_CODE_LABEL = "Zip Code";
    String BIRTHDAY_LABEL = "Birthday";
    String COLLEGE_LABEL = "College";
    String GRAD_YEAR_LABEL = "Grad Year";
    String MOBILE_LABEL = "Mobile";
    String STATE_ALABAMA_LABEL = "Alabama";
    String STATE_LABEL = "State";
    String SUGGESTIONS_LABEL = "SUGGESTIONS:";
    String LOG_OUT_LABEL = "LOG OUT";
    String EMAIL_LABEL = "Email";
    String OOPS_LABEL = "Oops!";
    String PASSWORD_LABEL = "Password";
    String UPGRADE_BUTTON_LABEL = "UPGRADE";
    String NOT_NOW_BUTTON_LABEL = "NOT NOW";
    String MY_BADGES_LABEL = "MY BADGES";
    String BADGE_UNLOCK_TITLE_LABEL = "BADGE UNLOCK";
    String PROUD_MEMBER_BADGE_DETAIL_COPY = "unlocked your first badge";
    String HOOKED_BADGE_DETAIL_COPY = "showing us some Bra Love";
    String SHOPAHOLIC_BADGE_DETAIL_COPY = "Cause you got your shop on";
    String SEE_ALL_BUTTON_LABEL = "SEE ALL BADGES";

    String BADGE_LABEL = "BADGE";
    String HOOKED_LABEL = "HOOKED";
    String PROUD_MEMBER_LABEL = "PROUD MEMBER";
    String SHOPAHOLIC_LABEL = "SHOPAHOLIC";
    String SO_EXCLUSIVE_LABEL = "SO EXCLUSIVE";
    String PINK_LOVE_LABEL = "PINK LOVE";
    String STYLE_CRUSH_LABEL = "STYLE CRUSH";


    String NOTIFICATION_OPTIN_LABEL = "Opt in to push notifications.";

    String SUBMIT_BUTTON_LABEL = "Submit";
    String DECLINE_BUTTON_LABEL = "Decline";
    String ALLOW_BUTTON_LABEL = "Allow";
    String DONT_ALLOW_BUTTON_LABEL = "Don't Allow";

    // Me and Badges
    String BADGE_IMAGE_LOCATOR = "//*[contains(@resource-id,'badge_image')]";
    String PROUD_MEMBER_BADGE_COPY_LOCATOR = "//*[contains(@text,'unlocked your first badge')]";
    String BADGE_WALLPAPER_BUTTON_LOCATOR = "//*[contains(@resource-id,'wallpaper_btn')]";
    String BADGE_BRAG_ABOUT_IT_BUTTON_LOCATOR = "//*[contains(@resource-id,'brag_btn')]";
    String PROUD_MEMBER_TEXT_LOCATOR = "//*[contains(@resource-id,'badge_text_view')] | //*[contains(@name,'PROUD MEMBER')]/..";

}
