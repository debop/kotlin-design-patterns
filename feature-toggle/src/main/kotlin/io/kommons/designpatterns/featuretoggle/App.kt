package io.kommons.designpatterns.featuretoggle

import io.kommons.designpatterns.featuretoggle.pattern.PropertiesFeatureToggleVersion
import io.kommons.designpatterns.featuretoggle.pattern.TieredFeatureToggleVersion
import io.kommons.logging.KotlinLogging
import java.util.Properties

/**
 * The Feature Toggle pattern allows for complete code executions to be turned on or off with ease.
 * This allows features to be controlled by either dynamic methods just as {@link User} information
 * or by {@link Properties}. In the App below there are two examples. Firstly the {@link Properties}
 * version of the feature toggle, where the enhanced version of the welcome message which is
 * personalised is turned either on or off at instance creation. This method is not as dynamic as
 * the {@link User} driven version where the feature of the personalised welcome message is
 * dependant on the {@link UserGroup} the {@link User} is in. So if the user is a memeber of the
 * {@link UserGroup#isPaid(User)} then they get an ehanced version of the welcome message.
 *
 * <p>Note that this pattern can easily introduce code complexity, and if not kept in check can
 * result in redundant unmaintained code within the codebase.
 */
class App

private val log = KotlinLogging.logger { }

/**
 * Block 1 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties}
 * setting the feature toggle to enabled.
 *
 * <p>Block 2 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties}
 * setting the feature toggle to disabled. Notice the difference with the printed welcome message
 * the username is not included.
 *
 * <p>Block 3 shows the {@link
 * com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion} being set up with
 * two users on who is on the free level, while the other is on the paid level. When the {@link
 * Service#getWelcomeMessage(User)} is called with the paid {@link User} note that the welcome
 * message contains their username, while the same service call with the free tier user is more
 * generic. No username is printed.
 *
 * @see User
 * @see UserGroup
 * @see Service
 * @see PropertiesFeatureToggleVersion
 * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
 */
fun main() {

    val properties = Properties().apply { put("enhancedWelcome", true) }
    val service = PropertiesFeatureToggleVersion(properties)
    val welcomeMessage = service.getWelcomeMessage(User("Jamie No Code"))
    println(welcomeMessage)

    // ----------------------------------

    val turnedOff = Properties().apply { put("enhancedWelcome", false) }
    val turnedOffService = PropertiesFeatureToggleVersion(turnedOff)
    val welcomeMessageTurnedOff = turnedOffService.getWelcomeMessage(User("Jamie No Code"))
    println(welcomeMessageTurnedOff)

    // ----------------------------------

    val service2 = TieredFeatureToggleVersion()

    val paidUser = User("Jamie Coder")
    val freeUser = User("Alan Defect")

    UserGroup.addUserToPaidGroup(paidUser)
    UserGroup.addUserToFreeGroup(freeUser)

    val welcomeMessagePaidUser = service2.getWelcomeMessage(paidUser)
    val welcomeMessageFreeUser = service2.getWelcomeMessage(freeUser)

    println(welcomeMessageFreeUser)
    println(welcomeMessagePaidUser)
}