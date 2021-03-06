package org.unbrokendome.gradle.plugins.helm.publishing.dsl

import org.gradle.api.Named
import org.gradle.api.provider.Property
import org.unbrokendome.gradle.plugins.helm.dsl.credentials.CredentialsContainer
import org.unbrokendome.gradle.plugins.helm.publishing.publishers.PublisherParams
import java.net.URI


/**
 * Describes a remote repository into which Helm charts can be published.
 */
interface HelmPublishingRepository : Named, CredentialsContainer {

    /**
     * The URL of the repository.
     */
    val url: Property<URI>
}


internal interface HelmPublishingRepositoryInternal : HelmPublishingRepository {

    /**
     * Gets the [PublisherParams] that contain all necessary information to publish a chart into this repository.
     */
    val publisherParams: PublisherParams
}
