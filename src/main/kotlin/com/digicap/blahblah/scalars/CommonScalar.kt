package com.digicap.blahblah.scalars

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.Scalars
import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring

@DgsComponent
class CommonScalar {

    @DgsRuntimeWiring
    fun addScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder
            .scalar(ExtendedScalars.newAliasedScalar("DateTime").aliasedScalar(ExtendedScalars.DateTime).build())
            .scalar(ExtendedScalars.newAliasedScalar("Time").aliasedScalar(ExtendedScalars.Time).build())
            .scalar(ExtendedScalars.newAliasedScalar("Base64String").aliasedScalar(Scalars.GraphQLString).build())
            .scalar(ExtendedScalars.newAliasedScalar("URL").aliasedScalar(Scalars.GraphQLString).build())
            .scalar(ExtendedScalars.newAliasedScalar("TimeZone").aliasedScalar(ExtendedScalars.Time).build())
            .scalar(ExtendedScalars.newAliasedScalar("EmailAddress").aliasedScalar(Scalars.GraphQLString).build())
            .scalar(ExtendedScalars.newAliasedScalar("UUID").aliasedScalar(Scalars.GraphQLString).build())
    }
}